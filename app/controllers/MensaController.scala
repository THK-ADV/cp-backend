package controllers

import mensa._
import play.api.mvc.{
  AbstractController,
  AnyContent,
  ControllerComponents,
  Request
}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class MensaController @Inject() (
    cc: ControllerComponents,
    val service: MensaService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  def legend() = Action.async { _ =>
    okSeq(service.fetchLegend())
  }

  def mensa(mensaParam: String) = Action.async { implicit r =>
    val res = for {
      mensa <- Future.fromTry(parseParam(mensaParam))
      menu <-
        if (parseBoolQueryParam("withLegend"))
          service.fetchMensaWithLegend(mensa)
        else
          service.fetchMensa(mensa)
    } yield menu

    okSeq(res)
  }

  private def parseBoolQueryParam(key: String)(implicit
      r: Request[AnyContent]
  ) =
    r.getQueryString(key).flatMap(_.toBooleanOption) getOrElse false

  private def parseParam(mensa: String): Try[MensaLocation] =
    mensa.toLowerCase match {
      case "gm" | "gummersbach" =>
        Success(MensaLocation.Gummersbach)
      case "dz" | "deutz " =>
        Success(MensaLocation.Deutz)
      case "st" | "südstadt" | "suedstadt" =>
        Success(MensaLocation.Suedstadt)
      case "c" | "claudiusstrasse" | "claudiusstraße" =>
        Success(MensaLocation.Claudiusstrasse)
      case "bdz" | "bistrodeutz" =>
        Success(MensaLocation.BistroDeutz)
      case s =>
        Failure(
          new Throwable(
            s"mensa parameter needs to be either 'gm', 'dz' or 'st', but was $s"
          )
        )
    }
}
