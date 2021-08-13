package noticeboard

import org.joda.time.LocalDateTime

case class NoticeboardEntry(
    title: String,
    description: String,
    detailUrl: String,
    published: LocalDateTime
)
