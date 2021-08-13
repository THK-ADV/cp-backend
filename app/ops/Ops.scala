package ops

import scala.concurrent.Future

object Ops {

  def toFuture[A](e: Either[String, A]): Future[A] = e match {
    case Right(a) =>
      Future.successful(a)
    case Left(err) =>
      Future.failed(new Throwable(err))
  }
}
