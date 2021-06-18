package service

import org.joda.time.LocalDate
import play.api.libs.ws.{WSClient, WSRequest}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.xml.{Elem, Node, NodeSeq}

@Singleton
class MensaService @Inject() (
    ws: WSClient,
    private implicit val ctx: ExecutionContext
) {

  private val baseUrl =
    "https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml"

  def get(mensa: Mensa) =
    ws.url(url(mensa)).get().map(r => validateResponse(r).map(parse))

  private def validateResponse(resp: WSRequest#Response): Either[String, Elem] =
    Either.cond(
      resp.status == 200 & resp.contentType == "text/xml",
      resp.xml,
      s"expected status to be 200, but was $resp.status. expected contentType to be text/xml, but was ${resp.contentType}"
    )

  private def parse(elem: Elem): Seq[Menu] =
    (elem \ "date").map { menu =>
      val timestamp = parseTimestamp(menu)
      val items = (menu \ "item").map(parseItem)
      Menu(timestamp, items)
    }

  private def parseTimestamp(node: Node): LocalDate = {
    val timestamp = node \@ "timestamp"
    new LocalDate(timestamp.toLong * 1000)
  }

  private def parseItem(item: Node): Item = {
    val category = parseString(item \ "category")
    val meal = parseMeal(item \ "meal", Meal.Main.apply)
    val desc = parseMeal(item \ "description", Meal.Description.apply)
    val foodIcons = parseStrings(item \ "foodicons")
    val prices = parsePrices(item)
    val (thumbnail, full) = parseImageUrl(item \ "foto")
    Item(category, meal, desc, foodIcons, prices, thumbnail, full)
  }

  private def parsePrices(node: Node): Set[Price] = {
    val student = parseDouble(node \ "price1")
    val employee = parseDouble(node \ "price2")
    val guest = parseDouble(node \ "price3")
    Set(
      Price(student, Role.Student),
      Price(employee, Role.Employee),
      Price(guest, Role.Guest)
    )
  }

  private def parseMeal[A <: Meal](
      node: NodeSeq,
      f: (String, String, List[Int], List[Int]) => A
  ): A = {
    val name = parseString(node \ "name")
    val nameAdditives = parseString(node \ "name_zus")
    val additives = parseInts(node \ "additives")
    val allergens = parseInts(node \ "allergens")
    f(name, nameAdditives, additives, allergens)
  }

  private def parseString(seq: NodeSeq): String =
    seq.text.trim

  private def parseInts(seq: NodeSeq): List[Int] = {
    val s = parseString(seq)
    if (s.isEmpty) Nil
    else
      s
        .split(", ")
        .map(_.toIntOption)
        .collect { case Some(a) => a }
        .toList
  }

  private def parseStrings(seq: NodeSeq): List[String] = {
    val s = parseString(seq)
    if (s.isEmpty) Nil
    else s.split(", ").toList
  }

  private def parseDouble(seq: NodeSeq): Option[Double] =
    parseString(seq)
      .replace(',', '.')
      .toDoubleOption

  private def parseImageUrl(seq: NodeSeq): (Option[String], Option[String]) = {
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

  private def filename(mensa: Mensa): String = mensa match {
    case Mensa.GM => "mensa_und_cafeteria_gummersbach_ml.xml"
    case Mensa.DZ => "mensa_und_cafeteria_deutz.xml"
    case Mensa.SS => "mensa_und_cafeteria_suedstadt.xml"
  }

  private def url(mensa: Mensa): String =
    s"$baseUrl/kstw/${filename(mensa)}"
}
