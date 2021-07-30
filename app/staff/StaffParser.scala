package staff

import java.net.URLDecoder
import scala.util.Try
import scala.xml.{Node, NodeSeq}

case class Staff(
    name: String,
    detailUrl: String,
    tel: Option[String],
    email: Option[String]
)

class StaffParser {

  import parser.PrimitivesParser._

  private val maxUrl =
    "https://www.th-koeln.de/hochschule/personen_3850.php?location_de%5B%5D=Campus+Gummersbach"

  private val url =
    "https://www.th-koeln.de/filter_list_more_person.php?language=de&location_de[]=Campus+Gummersbach&document_type[]=person&target=%2Fhochschule%2Fpersonen_3850.php&resultlayout=standard&start="

  def parseEntry(node: Node): Option[Staff] = {
    val tds = node \ "td"

    tds.size match {
      case 1 =>
        val (name, detailUrl) = parseName(tds.head \ "a")
        Some(Staff(name, detailUrl, None, None))
      case 2 =>
        val (name, detailUrl) = parseName(tds.head \ "a")
        val (tel, email) = parseCommunicationDetails(tds(1) \ "span")
        Some(Staff(name, detailUrl, tel, email))
      case _ =>
        None
    }
  }

  private def parseName(seq: NodeSeq): (String, String) =
    (parseString(seq), seq \@ "href")

  private def parseCommunicationDetails(
      seq: NodeSeq
  ): (Option[String], Option[String]) =
    seq.foldLeft((Option.empty[String], Option.empty[String])) {
      case ((tel, email), node) =>
        node \@ "class" match {
          case "tel"   => (parseNonEmptyString(node), email)
          case "email" => (tel, parseEmail(node))
          case _       => (tel, email)
        }
    }

   private def parseEmail(node: Node): Option[String] =
    parseNonEmptyString(node)
      .flatMap(s => Try(URLDecoder.decode(s, "utf-8")).toOption)
}
