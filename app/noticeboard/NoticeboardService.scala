package noticeboard

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class NoticeboardService @Inject() (
    private val dataProvider: NoticeboardDataProvider,
    private val parser: NoticeboardParser,
    private implicit val ctx: ExecutionContext
) {
  def fetchNoticeboard(feed: NoticeboardFeed): Future[Noticeboard] =
    dataProvider.noticeboard(feed).map(parser.parse)
}
