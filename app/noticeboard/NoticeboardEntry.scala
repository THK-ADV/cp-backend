package noticeboard

import org.joda.time.DateTime

case class NoticeboardEntry(
    title: String,
    description: String,
    detailUrl: String,
    published: DateTime
)
