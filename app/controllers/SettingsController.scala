package controllers

import mensa.MensaLocation
import newsfeed.Newsfeed
import noticeboard.NoticeBoardFeed
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import staff.StaffLocation

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class SettingsController @Inject() (
    cc: ControllerComponents,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc) {

  import MensaController.mensaLocationWrites
  import NewsfeedController.newsfeedWrites
  import NoticeBoardController.noticeBoardFeedWrites
  import StaffController.staffLocationWrites

  def settings() = Action { _ =>
    Ok(
      Json.obj(
        "mensaLocation" -> MensaLocation.all(),
        "newsfeed" -> Newsfeed.all(),
        "noticeBoardFeed" -> NoticeBoardFeed.all(),
        "staffLocation" -> StaffLocation.all()
      )
    )
  }

  def mensa() = Action { _ =>
    Ok(Json.toJson(MensaLocation.all()))
  }

  def newsfeed() = Action { _ =>
    Ok(Json.toJson(Newsfeed.all()))
  }

  def noticeboard() = Action { _ =>
    Ok(Json.toJson(NoticeBoardFeed.all()))
  }

  def staff() = Action { _ =>
    Ok(Json.toJson(StaffLocation.all()))
  }
}
