package controllers

import mensa._
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object MensaController {
  implicit val mensaLocationWrites: Writes[MensaLocation] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))
}

@Singleton
class MensaController @Inject() (
    cc: ControllerComponents,
    val service: MensaService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse
    with QueryStringParser {

  import MensaController.mensaLocationWrites

  def legend() = Action.async { _ =>
    okSeq(service.fetchLegend())
  }

  def mensa(id: String) = Action.async { implicit r =>
    val res =
      if (parseBoolQueryParam("withLegend"))
        fetchWithLegend(id)
      else
        fetchMenu(id)
    okSeq(res)
  }

  def fetchMenu(id: String): Future[Seq[Menu]] =
    for {
      mensa <- Future.fromTry(parseParam(id))
      menu <- service.fetchMensa(mensa)
    } yield menu

  def fetchWithLegend(id: String): Future[Seq[Menu]] =
    for {
      mensa <- Future.fromTry(parseParam(id))
      menu <- service.fetchMensaWithLegend(mensa)
    } yield menu

  def allAvailable() = Action { _ =>
    Ok(Json.toJson(MensaLocation.all()))
  }

  private def parseParam(mensa: String): Try[MensaLocation] =
    MensaLocation.apply(mensa.toLowerCase) match {
      case Some(value) =>
        Success(value)
      case None =>
        Failure(
          new Throwable(
            s"invalid parameter. the following locations are supported: ${MensaLocation.all().map(_.id).mkString(",")}"
          )
        )
    }
}
