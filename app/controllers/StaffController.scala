package controllers

import play.api.mvc.{AbstractController, ControllerComponents}
import staff.{StaffLocation, StaffService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class StaffController @Inject() (
    cc: ControllerComponents,
    val service: StaffService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  // TODO add caching via http header
  def staffs(staffLocation: String) = Action.async { _ =>
    val res = for {
      location <- Future.fromTry(parseStaffLocation(staffLocation))
      staffs <- service.fetchStaff(location)
    } yield staffs
    okSeq(res)
  }

  private def parseStaffLocation(staff: String): Try[StaffLocation] =
    staff.toLowerCase match {
      case "gm" | "gummersbach"            => Success(StaffLocation.Gummersbach)
      case "dz" | "deutz "                 => Success(StaffLocation.Deutz)
      case "st" | "südstadt" | "suedstadt" => Success(StaffLocation.Suedstadt)
      case "lev" | "leverkusen"            => Success(StaffLocation.Leverkusen)
      case s =>
        Failure(
          new Throwable(
            s"staff parameter needs to be either 'gm', 'dz', 'st' or 'lev', but was $s"
          )
        )
    }
}
