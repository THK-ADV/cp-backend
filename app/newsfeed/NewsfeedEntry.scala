package newsfeed

case class NewsfeedEntry(
    title: String,
    body: String,
    detailUrl: String,
    imgUrl: Option[String]
)
