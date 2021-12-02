package controllers.legacy

import controllers.NoticeboardController
import controllers.legacy.LegacyNoticeboardController.parseLegacyNoticeboard
import noticeboard.{Noticeboard, NoticeboardEntry}
import ops.DateTimeFormatOps
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

object LegacyNoticeboardController {
  def parseLegacyNoticeboard(nb: Noticeboard, isoDate: Boolean): JsValue = {
    val dateFormat =
      if (isoDate) DateTimeFormatOps.isoDate
      else DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    def go(e: NoticeboardEntry): JsValue =
      Json.obj(
        "title" -> e.title,
        "url" -> e.detailUrl.stripPrefix("https://www.th-koeln.de/"),
        "text" -> e.description,
        "date" -> dateFormat.print(e.published)
      )

    Json.obj(
      "status" -> 1,
      "origin" -> "",
      "module" -> "pinboard",
      "pinboard" -> "",
      "data" -> nb.entries.map(go)
    )
  }
}

@Singleton
class LegacyNoticeboardController @Inject() (
    cc: ControllerComponents,
    val controller: NoticeboardController,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc) {

  def noticeboard(id: String) = Action.async { request =>
    val isoDate = request
      .getQueryString("date")
      .fold(false)(_.toLowerCase == "iso")

    controller
      .fetchNoticeboard(id)
      .map(nb => Ok(parseLegacyNoticeboard(nb, isoDate)))
  }
}
