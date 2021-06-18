package mensa

import org.joda.time.LocalDate

import scala.xml.{Elem, Node, NodeSeq}

class MensaParser(baseUrl: String) {

  def parseMenu(elem: Elem): Seq[Menu] =
    for {
      menu <- elem \ "date"
      timestamp = parseTimestamp(menu) if timestamp.isDefined
    } yield {
      val items = (menu \ "item").map(parseItem)
      Menu(timestamp.get, items)
    }

  def parseTimestamp(node: Node): Option[LocalDate] = {
    (node \@ "timestamp").toLongOption
      .map(l => new LocalDate(l * 1000))
  }

  def parseItem(item: Node): Item = {
    val category = parseString(item \ "category")
    val meal = parseMeal(item \ "meal", Meal.Main.apply)
    val desc = parseMeal(item \ "description", Meal.Description.apply)
    val foodIcons = parseStrings(item \ "foodicons")
    val prices = parsePrices(item)
    val (thumbnail, full) = parseImageUrl(item \ "foto")
    Item(category, meal, desc, foodIcons, prices, thumbnail, full)
  }

  def parsePrices(node: Node): Set[Price] = {
    val student = parseDouble(node \ "price1")
    val employee = parseDouble(node \ "price2")
    val guest = parseDouble(node \ "price3")
    Set(
      Price(student, Role.Student),
      Price(employee, Role.Employee),
      Price(guest, Role.Guest)
    )
  }

  def parseMeal[A <: Meal](
      node: NodeSeq,
      f: (String, String, List[Int], List[Int]) => A
  ): A = {
    val name = parseString(node \ "name")
    val nameAdditives = parseString(node \ "name_zus")
    val additives = parseInts(node \ "additives")
    val allergens = parseInts(node \ "allergens")
    f(name, nameAdditives, additives, allergens)
  }

  def parseString(seq: NodeSeq): String =
    seq.text.trim

  def parseInts(seq: NodeSeq): List[Int] = {
    val s = parseString(seq)
    if (s.isEmpty) Nil
    else
      s
        .split(", ")
        .map(_.toIntOption)
        .collect { case Some(a) => a }
        .toList
  }

  def parseStrings(seq: NodeSeq): List[String] = {
    val s = parseString(seq)
    if (s.isEmpty) Nil
    else s.split(", ").toList
  }

  def parseDouble(seq: NodeSeq): Option[Double] =
    parseString(seq)
      .replace(',', '.')
      .toDoubleOption

  def parseImageUrl(seq: NodeSeq): (Option[String], Option[String]) = {
    def prefixBaseUrl(url: String) =
      s"$baseUrl/$url"

    val thumbnail = parseString(seq)
    val thumbnailSuffix = "_r480_cropped"

    (
      Option.when(thumbnail.nonEmpty)(prefixBaseUrl(thumbnail)),
      Option.when(thumbnail.contains(thumbnailSuffix))(
        prefixBaseUrl(thumbnail.replace(thumbnailSuffix, ""))
      )
    )
  }
}
