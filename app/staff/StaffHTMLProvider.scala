package staff

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

trait StaffHTMLProvider {
  def maxResults(location: StaffLocation): Future[Browser#DocumentType]
  def staffs(location: StaffLocation, batch: Int): Future[Browser#DocumentType]
}

final class HttpStaffHTMLProvider @Inject() (
    private val ws: WSClient,
    private val config: StaffConfig,
    private implicit val ctx: ExecutionContext
) extends StaffHTMLProvider {

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
