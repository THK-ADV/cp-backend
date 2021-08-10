package noticeboard

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import noticeboard.NoticeBoardFeed._
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

trait NoticeBoardDataProvider {
  def noticeBoard(feed: NoticeBoardFeed): Future[Browser#DocumentType]
}

final class NoticeBoardRSSFeedProvider @Inject() (
    private val ws: WSClient,
    private val config: NoticeBoardConfig,
    private implicit val ctx: ExecutionContext
) extends NoticeBoardDataProvider {

  private val browser = JsoupBrowser()

  override def noticeBoard(
      feed: NoticeBoardFeed
  ): Future[Browser#DocumentType] =
    ws.url(url(feed)).get().map(r => browser.parseString(r.body))

  private def url(feed: NoticeBoardFeed): String = {
    val suffix = feed match {
      case Informatik =>
        "schwarzes-brett--informatik_33871.php"
      case Ingenieurwissenschaften =>
        "schwarzes-brett---grundstudium-ingenieurwissenschaften_33873.php"
      case Maschinenbau | Wirtschaftsingenieurwesen | Produktdesign |
          Prozessentwicklung =>
        "schwarzes-brett---maschinenbau_33874.php"
      case Elektrotechnik | AutomationAndIT =>
        "schwarzes-brett---elektrotechnik-und-automation-and-it_33875.php"
    }

    config.baseUrl + suffix
  }
}
