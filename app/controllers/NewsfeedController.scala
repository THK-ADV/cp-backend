package controllers

import newsfeed.{Newsfeed, NewsfeedEntry, NewsfeedService}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object NewsfeedController extends JsonNullWritable {
  implicit val newsfeedWrites: Writes[Newsfeed] =
    Writes.apply(a => Json.obj("label" -> a.label, "id" -> a.id))

  implicit val newsfeedEntryWrites: Writes[NewsfeedEntry] =
    Json.writes[NewsfeedEntry]
}

@Singleton
class NewsfeedController @Inject() (
    cc: ControllerComponents,
    val service: NewsfeedService,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  import NewsfeedController._

  def newsfeed(id: String) = Action.async { _ =>
    okSeq(fetchNewsfeed(id))
  }

  def fetchNewsfeed(id: String) =
    for {
      feed <- Future.fromTry(parseNewsfeed(id))
      newsfeed <- service.fetchNewsfeed(feed)
    } yield newsfeed

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
