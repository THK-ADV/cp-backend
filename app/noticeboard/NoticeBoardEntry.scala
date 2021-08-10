package noticeboard

import org.joda.time.LocalDateTime

case class NoticeBoardEntry(
    title: String,
    description: String,
    detailUrl: String,
    published: LocalDateTime
)
