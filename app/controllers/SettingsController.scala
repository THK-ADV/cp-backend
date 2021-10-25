package controllers

import mensa.MensaLocation
import newsfeed.Newsfeed
import noticeboard.NoticeboardFeed
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
  import NoticeboardController.noticeboardFeedWrites
  import StaffController.staffLocationWrites

  def settings() = Action { _ =>
    Ok(
      Json.obj(
        "mensaLocation" -> MensaLocation.all(),
        "newsfeed" -> Newsfeed.all(),
        "noticeBoardFeed" -> NoticeboardFeed.all(),
        "staffLocation" -> StaffLocation.all()
      )
    )
  }
}
