package mensa

import play.api.libs.ws.{WSClient, WSRequest}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.xml.Elem

@Singleton
class MensaService @Inject() (
    ws: WSClient,
    private implicit val ctx: ExecutionContext
) {

  private val baseUrl =
    "https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml"

  private val parser = new MensaParser(baseUrl)

  def get(mensa: Mensa) =
    ws.url(url(mensa)).get().map(r => validateResponse(r).map(parser.parseMenu))

  private def validateResponse(resp: WSRequest#Response): Either[String, Elem] =
    Either.cond(
      resp.status == 200 & resp.contentType == "text/xml",
      resp.xml,
      s"expected status to be 200, but was $resp.status. expected contentType to be text/xml, but was ${resp.contentType}"
    )

  private def filename(mensa: Mensa): String = mensa match {
    case Mensa.GM => "mensa_und_cafeteria_gummersbach_ml.xml"
    case Mensa.DZ => "mensa_und_cafeteria_deutz.xml"
    case Mensa.SS => "mensa_und_cafeteria_suedstadt.xml"
  }

  private def url(mensa: Mensa): String =
    s"$baseUrl/kstw/${filename(mensa)}"
}
