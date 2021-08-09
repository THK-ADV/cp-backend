package mensa

import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.xml.Elem

trait MensaXMLProvider {
  def mensaMenu(mensa: Mensa): Future[Elem]
  def legend(): Future[Elem]
}

class HttpMensaXMLProvider @Inject() (
    private val ws: WSClient,
    private val config: MensaConfig,
    private implicit val ctx: ExecutionContext
) extends MensaXMLProvider {

  override def mensaMenu(mensa: Mensa): Future[Elem] =
    ws.url(url(mensa)).get().map(_.xml)

  override def legend(): Future[Elem] =
    ws.url(config.legendUrl).get().map(_.xml)

  private def filename(mensa: Mensa): String = mensa match {
    case Mensa.Gummersbach => "mensa_und_cafeteria_gummersbach_ml.xml"
    case Mensa.Deutz       => "mensa_und_cafeteria_deutz.xml"
    case Mensa.Suedstadt   => "mensa_und_cafeteria_suedstadt.xml"
  }

  private def url(mensa: Mensa): String =
    s"${config.baseUrl}/kstw/${filename(mensa)}"
}
