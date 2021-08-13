package noticeboard

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import noticeboard.NoticeboardFeed._
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

trait NoticeboardDataProvider {
  def noticeboard(feed: NoticeboardFeed): Future[Browser#DocumentType]
}

final class NoticeboardRSSFeedProvider @Inject() (
    private val ws: WSClient,
    private val config: NoticeboardConfig,
    private implicit val ctx: ExecutionContext
) extends NoticeboardDataProvider {

  private val browser = JsoupBrowser()

  override def noticeboard(
      feed: NoticeboardFeed
  ): Future[Browser#DocumentType] =
    ws.url(url(feed)).get().map(r => browser.parseString(r.body))

  private def url(feed: NoticeboardFeed): String = {
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
