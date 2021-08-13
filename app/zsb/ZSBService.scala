package zsb

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class ZSBService @Inject() (
    private val dataProvider: ZSBDataProvider,
    private val parser: ZSBParser,
    private val contactInfo: ZSBContact,
    private implicit val ctx: ExecutionContext
) {

  def fetchZSBFeed(): Future[(ZSBFeed, ZSBContactInfo)] =
    for {
      doc <- dataProvider.zsbFeed()
      feed <- toFuture(parser.parse(doc))
    } yield (
      feed,
      ZSBContactInfo(
        contactInfo.introduction,
        ZSBAction("Anrufen", contactInfo.phone),
        ZSBAction("Email", contactInfo.email)
      )
    )

  private def toFuture[A](e: Either[String, A]): Future[A] = e match {
    case Right(a) =>
      Future.successful(a)
    case Left(err) =>
      Future.failed(new Throwable(err))
  }
}
