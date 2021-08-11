package newsfeed

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

trait NewsfeedDataProvider {
  def newsfeed(feed: Newsfeed): Future[Browser#DocumentType]
}

final class NewsfeedHTMLProvider @Inject() (
    private val ws: WSClient,
    private val config: NewsfeedConfig,
    private implicit val ctx: ExecutionContext
) extends NewsfeedDataProvider {

  private val browser = JsoupBrowser()

  override def newsfeed(feed: Newsfeed): Future[Browser#DocumentType] =
    ws.url(url(feed)).get().map(r => browser.parseString(r.body))

  private def url(feed: Newsfeed): String = feed match {
    case Newsfeed.General => config.generalUrl
    case _                => config.facultyBaseUrl + feed.label.replace(' ', '+')
  }
}
