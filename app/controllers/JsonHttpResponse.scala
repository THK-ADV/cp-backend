package controllers

import play.api.libs.json.{JsObject, Json, Writes}
import play.api.mvc.Result
import play.api.mvc.Results.{BadRequest, Created, NotFound, Ok}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

trait JsonHttpResponse {
  protected implicit val ctx: ExecutionContext

  private def recover400[A](f: Future[Result])(implicit
      writes: Writes[A]
  ): Future[Result] =
    f.recover { case NonFatal(t) => badRequest(t) }

  def okSeq[A](f: Future[Seq[A]])(implicit writes: Writes[A]): Future[Result] =
    recover400(f.map(s => Ok(Json.toJson(s))))

  def ok[A](f: Future[A])(implicit writes: Writes[A]): Future[Result] =
    recover400(f.map(s => Ok(Json.toJson(s))))

  def okOpt[A](f: Future[Option[A]])(implicit
      writes: Writes[A]
  ): Future[Result] =
    recover400(
      f.map(s => s.fold(NotFound(Json.obj()))(s => Ok(Json.toJson(s))))
    )

  def created[A](f: Future[A])(implicit writes: Writes[A]): Future[Result] =
    recover400(f.map(s => Created(Json.toJson(s))))

  def err(t: Throwable): JsObject = Json.obj("msg" -> t.getMessage)

  def badRequest(t: Throwable): Result = BadRequest(err(t))
}
