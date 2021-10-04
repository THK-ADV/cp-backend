package controllers

import noticeboard.{
  Noticeboard,
  NoticeboardEntry,
  NoticeboardFeed,
  NoticeboardService
}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object NoticeboardController extends LocalDateTimeFormat {
  implicit val noticeboardFeedWrites: Writes[NoticeboardFeed] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))

  implicit val noticeboardEntryFeedWrites: Writes[NoticeboardEntry] =
    Json.writes[NoticeboardEntry]

  implicit val noticeboardWrites: Writes[Noticeboard] = Json.writes[Noticeboard]
}

@Singleton
class NoticeboardController @Inject() (
    cc: ControllerComponents,
    val service: NoticeboardService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  import NoticeboardController._

  def noticeboard(id: String) = Action.async { _ =>
    ok(fetchNoticeboard(id))
  }

  def fetchNoticeboard(id: String) =
    for {
      feed <- Future.fromTry(parseNoticenoardFeed(id))
      noticeBoard <- service.fetchNoticeboard(feed)
    } yield noticeBoard

  def allAvailable() = Action { _ =>
    Ok(Json.toJson(NoticeboardFeed.all()))
  }

  private def parseNoticenoardFeed(feed: String): Try[NoticeboardFeed] =
    NoticeboardFeed.apply(feed.toLowerCase) match {
      case Some(value) =>
        Success(value)
      case None =>
        Failure(
          new Throwable(
            s"invalid parameter. the following feeds are supported: ${NoticeboardFeed.all().map(_.id).mkString(",")}"
          )
        )
    }
}
