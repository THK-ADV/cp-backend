package newsfeed

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class NewsfeedService @Inject() (
    private val dataProvider: NewsfeedDataProvider,
    private val parser: NewsfeedParser,
    private implicit val ctx: ExecutionContext
) {
  def fetchNewsfeed(feed: Newsfeed): Future[List[NewsfeedEntry]] =
    dataProvider.newsfeed(feed).map(parser.parse)
}
