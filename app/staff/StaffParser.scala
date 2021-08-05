package staff

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import play.api.libs.ws.WSClient

import java.net.URLDecoder
import javax.inject.{Inject, Singleton}
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

case class StaffConfig(
    baseMaxUrl: String,
    baseUrlPrefix: String,
    baseUrlSuffix: String
)

trait StaffXmlProvider {
  def maxResults(location: StaffLocation): Future[Browser#DocumentType]
  def staffs(location: StaffLocation, batch: Int): Future[Browser#DocumentType]
}

final class HttpStaffXmlProvider @Inject() (
    private val ws: WSClient,
    private val config: StaffConfig,
    private implicit val ctx: ExecutionContext
) extends StaffXmlProvider {

  private val browser = JsoupBrowser()

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
  override def maxResults(
      location: StaffLocation
  ): Future[Browser#DocumentType] =
    ws.url(maxUrl(location)).get().map(r => browser.parseString(r.body))

  override def staffs(
      location: StaffLocation,
      batch: Int
  ): Future[Browser#DocumentType] =
    ws.url(s"${baseUrl(location)}$batch")
      .get()
      .map(r => browser.parseString(r.body))
}

@Singleton
final class StaffService @Inject() (
    private val xmlProvider: StaffXmlProvider,
    private implicit val ctx: ExecutionContext
) {

  private val parser = new StaffParser()

  def fromCache(location: StaffLocation): Future[List[Staff]] = ???

  def writeCache(staff: List[Staff]): Unit = ???

  def fetchMaxResults(location: StaffLocation): Future[Int] =
    xmlProvider.maxResults(location).flatMap { xml =>
      parser
        .parseMaxResults(xml)
        .fold(Future.failed[Int](new Throwable("can't find max results")))(
          Future.successful
        )
    }

/*  def fetchStaff(location: StaffLocation): Future[List[Staff]] =
    for {
      max <- fetchMaxResults(location)
      steps = this.steps(0, max, 10)
      results <- Future.sequence(
        steps.map(s => xmlProvider.staffs(location, s))
      )
    } yield results.flatMap(parser.parseEntries)*/

  def steps(start: Int, end: Int, step: Int): List[Int] = {
    if (end == 0) return Nil
    val xs = ListBuffer.empty[Int]
    var i = start
    while (i < end) {
      xs += i
      i += step
    }
    xs.toList
  }
}

final class StaffParser {

  import parser.PrimitivesParser._

  def parseAttr[A](
      e: Element,
      name: String,
      f: String => Option[A]
  ): Option[A] = {
    println(e)
    if (e.hasAttr(name)) f(e.attr(name)) else None
  }

  def parseMaxResults(doc: Browser#DocumentType): Option[Int] =
    (doc >?> element("#filter_list_more"))
      .flatMap(e => parseAttr(e, "data-maxresults", _.toIntOption))

 /* def parseEntries(node: Node): Seq[Staff] =
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
      .flatMap(s => Try(URLDecoder.decode(s, "utf-8")).toOption)*/
}
