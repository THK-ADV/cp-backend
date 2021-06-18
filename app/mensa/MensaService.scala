package mensa

import play.api.libs.ws.WSClient

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class MensaService @Inject() (
    ws: WSClient,
    private implicit val ctx: ExecutionContext
) {

  private val baseUrl =
    "https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml"

  private val parser = new MensaParser(baseUrl)

  def get(mensa: Mensa) =
    for {
      resp <- ws.url(url(mensa)).get()
      if resp.status == 200 & resp.contentType == "text/xml"
    } yield parser.parseMenu(resp.xml)

  private def filename(mensa: Mensa): String = mensa match {
    case Mensa.Gummersbach => "mensa_und_cafeteria_gummersbach_ml.xml"
    case Mensa.Deutz       => "mensa_und_cafeteria_deutz.xml"
    case Mensa.Suedstadt   => "mensa_und_cafeteria_suedstadt.xml"
  }

  private def url(mensa: Mensa): String =
    s"$baseUrl/kstw/${filename(mensa)}"
}
