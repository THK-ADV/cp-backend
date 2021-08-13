package zsb

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

trait ZSBDataProvider {
  def zsbFeed(): Future[Browser#DocumentType]
}

final class ZSBHTMLProvider @Inject() (
    private val ws: WSClient,
    private val config: ZSBConfig,
    private implicit val ctx: ExecutionContext
) extends ZSBDataProvider {

  private val browser = JsoupBrowser()

  override def zsbFeed(): Future[Browser#DocumentType] =
    ws.url(config.baseUrl).get().map(r => browser.parseString(r.body))
}
