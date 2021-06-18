package controllers

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import service._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class MensaController @Inject() (
    cc: ControllerComponents,
    val service: MensaService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc) {

  def mensa() = Action.async { _ =>
    service.get(Mensa.DZ).map {
      case Right(menu) => Ok(Json.toJson(menu))
      case Left(err)   => BadRequest(Json.obj("err" -> err))
    }
  }
}
