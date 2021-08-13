package controllers

import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}
import zsb._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

object ZSBController extends JsonNullWritable {
  private implicit val zsbFeedEntryWrites: Writes[ZSBEntry] =
    Json.writes[ZSBEntry]

  private implicit val zsbFeedWrites: Writes[ZSBFeed] =
    Json.writes[ZSBFeed]

  private implicit val actionWrites: Writes[ZSBAction] =
    Json.writes[ZSBAction]

  private implicit val infoWrites: Writes[ZSBContactInfo] =
    Json.writes[ZSBContactInfo]

  implicit val zsbFeedInfoWrites: Writes[(ZSBFeed, ZSBContactInfo)] =
    Writes.apply(a => Json.obj("contact" -> a._2, "feed" -> a._1))
}

@Singleton
class ZSBController @Inject() (
    cc: ControllerComponents,
    val service: ZSBService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  import ZSBController.zsbFeedInfoWrites

  def zsbFeed() = Action.async { _ =>
    ok(service.fetchZSBFeed())
  }
}
