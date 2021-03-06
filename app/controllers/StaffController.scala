package controllers

import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}
import staff.{StaffLocation, StaffService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object StaffController {
  implicit val staffLocationWrites: Writes[StaffLocation] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))
}

@Singleton
class StaffController @Inject() (
    cc: ControllerComponents,
    val service: StaffService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  import StaffController.staffLocationWrites

  // TODO add caching via http header
  def staffs(id: String) = Action.async { _ =>
    val res = for {
      location <- Future.fromTry(parseStaffLocation(id))
      staffs <- service.fetchStaff(location)
    } yield staffs
    okSeq(res)
  }

  def allAvailable() = Action { _ =>
    Ok(Json.toJson(StaffLocation.all()))
  }

  private def parseStaffLocation(staff: String): Try[StaffLocation] =
    StaffLocation.apply(staff.toLowerCase) match {
      case Some(value) =>
        Success(value)
      case None =>
        Failure(
          new Throwable(
            s"invalid parameter. the following locations are supported: ${StaffLocation.all().map(_.id).mkString(",")}"
          )
        )
    }
}
