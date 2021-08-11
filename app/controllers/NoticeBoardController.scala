package controllers

import noticeboard.{
  NoticeBoard,
  NoticeBoardEntry,
  NoticeBoardFeed,
  NoticeBoardService
}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class NoticeBoardController @Inject() (
    cc: ControllerComponents,
    val service: NoticeBoardService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse
    with LocalDateTimeFormat {

  implicit val writesFeed: Writes[NoticeBoardFeed] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))

  implicit val writesEntry: Writes[NoticeBoardEntry] =
    Json.writes[NoticeBoardEntry]

  implicit val writesBoard: Writes[NoticeBoard] = Json.writes[NoticeBoard]

  def noticeBoard(id: String) = Action.async { _ =>
    val res = for {
      feed <- Future.fromTry(parseNoticeBoardFeed(id))
      noticeBoard <- service.fetchNoticeBoard(feed)
    } yield noticeBoard
    ok(res)
  }

  def allAvailable() = Action { _ =>
    Ok(Json.toJson(NoticeBoardFeed.all()))
  }

  private def parseNoticeBoardFeed(feed: String): Try[NoticeBoardFeed] =
    NoticeBoardFeed.apply(feed.toLowerCase) match {
      case Some(value) =>
        Success(value)
      case None =>
        Failure(
          new Throwable(
            s"invalid parameter. the following feeds are supported: ${NoticeBoardFeed.all().map(_.id).mkString(",")}"
          )
        )
    }
}
