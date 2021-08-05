package staff

import play.api.libs.ws.WSClient

import java.net.URLDecoder
import javax.inject.Inject
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try
import scala.xml.{Node, NodeSeq}

case class Staff(
    name: String,
    detailUrl: String,
    tel: Option[String],
    email: Option[String]
)

sealed trait StaffLocation

object StaffLocation {
  case object Gummersbach extends StaffLocation
  case object Deutz extends StaffLocation
  case object Suedstadt extends StaffLocation
  case object Leverkusen extends StaffLocation
}

// baseMaxUrl       https://www.th-koeln.de/hochschule/personen_3850.php?location_de%5B%5D=Campus+
// baseUrlPrefix    https://www.th-koeln.de/filter_list_more_person.php?language=de&location_de[]=Campus+
// baseUrlSuffix    &document_type[]=person&target=%2Fhochschule%2Fpersonen_3850.php&resultlayout=standard&start=
case class StaffConfig(
    baseMaxUrl: String,
    baseUrlPrefix: String,
    baseUrlSuffix: String
)

final class StaffService @Inject() (
    private val ws: WSClient,
    private val config: StaffConfig,
    private implicit val ctx: ExecutionContext
) {

  private val parser = new StaffParser()

  def fromCache(location: StaffLocation): Future[List[Staff]] = ???

  def writeCache(staff: List[Staff]): Unit = ???

  def fetchStaff(location: StaffLocation): Future[List[Staff]] = {
    for {
      maxResp <- ws.url(maxUrl(location)).get()
      _ = println(maxResp.status, maxResp.contentType)
      max = parser.parseMaxResults(maxResp.xml) if max.isDefined
      _ = println(max)
      steps = this.steps(0, max.get, 10)
      results <- Future.sequence(
        steps.map(s => ws.url(s"${baseUrl(location)}$s").get())
      )
      staffs = results.flatMap(r => parser.parseEntries(r.xml))
    } yield staffs
  }

  def steps(start: Int, end: Int, step: Int): List[Int] = {
    val xs = ListBuffer.empty[Int]
    var i = start
    while (i < end + step) {
      xs += i
      i += step
    }
    xs.toList
  }

  private def maxUrl(location: StaffLocation): String =
    s"${config.baseMaxUrl}${locationName(location)}"

  private def baseUrl(location: StaffLocation): String =
    s"${config.baseUrlPrefix}${locationName(location)}${config.baseUrlSuffix}"

  private def locationName(location: StaffLocation): String = location match {
    case StaffLocation.Gummersbach => "Gummersbach"
    case StaffLocation.Deutz       => "Deutz"
    case StaffLocation.Suedstadt   => "S%C3%BCdstadt"
    case StaffLocation.Leverkusen  => "Leverkusen"
  }
}

final class StaffParser {

  import parser.PrimitivesParser._

  def parseMaxResults(node: Node): Option[Int] =
    (node \\ "a").foldLeft(Option.empty[Int]) { case (acc, n) =>
      acc.orElse((n \@ "data-maxresults").toIntOption)
    }

  def parseEntries(node: Node): Seq[Staff] =
    (node \ "tr").flatMap(parseEntry)

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
