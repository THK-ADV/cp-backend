package controllers

import mensa._
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class MensaController @Inject() (
    cc: ControllerComponents,
    val service: MensaService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse
    with QueryStringParser {

  implicit val writes: Writes[MensaLocation] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))

  def legend() = Action.async { _ =>
    okSeq(service.fetchLegend())
  }

  def mensa(id: String) = Action.async { implicit r =>
    val res = for {
      mensa <- Future.fromTry(parseParam(id))
      menu <-
        if (parseBoolQueryParam("withLegend"))
          service.fetchMensaWithLegend(mensa)
        else
          service.fetchMensa(mensa)
    } yield menu

    okSeq(res)
  }

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
