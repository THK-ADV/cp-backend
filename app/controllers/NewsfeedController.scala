package controllers

import newsfeed.{Newsfeed, NewsfeedEntry, NewsfeedService}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class NewsfeedController @Inject() (
    cc: ControllerComponents,
    val service: NewsfeedService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse
    with JsonNullWritable {

  implicit val writesFeed: Writes[Newsfeed] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))

  implicit val writesEntry: Writes[NewsfeedEntry] = Json.writes[NewsfeedEntry]

  def newsfeed(id: String) = Action.async { _ =>
    val res = for {
      feed <- Future.fromTry(parseNewsfeed(id))
      noticeBoard <- service.fetchNewsfeed(feed)
    } yield noticeBoard
    okSeq(res)
  }

  def allAvailable() = Action { _ =>
    Ok(Json.toJson(Newsfeed.all()))
  }

  private def parseNewsfeed(feed: String): Try[Newsfeed] =
    Newsfeed.apply(feed) match {
      case Some(value) =>
        Success(value)
      case None =>
        Failure(
          new Throwable(
            s"invalid parameter. the following feeds are supported: ${Newsfeed.all().map(_.id).mkString(",")}"
          )
        )
    }
}
