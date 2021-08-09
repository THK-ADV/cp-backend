package mensa

import mensa.AdditiveType.ID
import org.joda.time.LocalDate
import parser.PrimitivesParser._

import java.util.Locale
import javax.inject.{Inject, Singleton}
import scala.xml.{Elem, Node, NodeSeq}

@Singleton
class MensaParser @Inject() (
    private val config: MensaConfig
) {

  private val locale: Locale = Locale.GERMANY

  def parseMenu(elem: Elem): Seq[Menu] =
    for {
      menu <- elem \ "date"
      timestamp = parseTimestamp(menu) if timestamp.isDefined
    } yield {
      val timestamp0 = timestamp.get
      val weekday = timestamp0.dayOfWeek().getAsText(locale)
      val items = (menu \ "item").map(parseItem)
      Menu(timestamp0, weekday, items)
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
      f: (String, String, List[ID], List[ID]) => A
  ): A = {
    val name = parseString(node \ "name")
    val nameAdditives = parseString(node \ "name_zus")
    val additives = parseIDs(node \ "additives")
    val allergens = parseIDs(node \ "allergens")
    f(name, nameAdditives, additives, allergens)
  }

  def parseIDs(seq: NodeSeq): List[ID] =
    parseInts(seq).map(ID.apply)

  def parseImageUrl(seq: NodeSeq): (Option[String], Option[String]) = {
    def prefixBaseUrl(url: String) =
      s"${config.baseUrl}/$url"

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
