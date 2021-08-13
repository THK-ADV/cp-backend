package noticeboard

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class NoticeboardService @Inject() (
    private val dataProvider: NoticeboardDataProvider,
    private val parser: NoticeboardParser,
    private implicit val ctx: ExecutionContext
) {

  import ops.Ops.toFuture

  def fetchNoticeboard(feed: NoticeboardFeed): Future[Noticeboard] =
    dataProvider.noticeboard(feed).flatMap(a => toFuture(parser.parse(a)))
}
