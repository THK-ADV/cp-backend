package controllers.legacy

import controllers.NewsfeedController
import controllers.legacy.LegacyNewsfeedController.parseLegacyNewsfeed
import newsfeed.NewsfeedEntry
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

object LegacyNewsfeedController {

  def parseLegacyNewsfeed(feed: List[NewsfeedEntry]): JsValue = {
    def go(e: NewsfeedEntry): JsValue =
      Json.obj(
        "title" -> e.title,
        "text" -> e.body,
        "url" -> e.detailUrl,
        "image" -> e.imgUrl
      )

    Json.obj(
      "status" -> 200,
      "origin" -> "",
      "data" -> feed.map(go)
    )
  }
}

@Singleton
class LegacyNewsfeedController @Inject() (
    cc: ControllerComponents,
    val controller: NewsfeedController,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc) {

  def newsfeed(id: String) = Action.async { _ =>
    controller.fetchNewsfeed(id).map(f => Ok(parseLegacyNewsfeed(f)))
  }
}
