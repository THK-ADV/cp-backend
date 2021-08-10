package noticeboard

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class NoticeBoardService @Inject() (
    private val dataProvider: NoticeBoardDataProvider,
    private val parser: NoticeBoardParser,
    private implicit val ctx: ExecutionContext
) {
  def fetchNoticeBoard(feed: NoticeBoardFeed): Future[NoticeBoard] =
    dataProvider.noticeBoard(feed).map(parser.parse)
}
