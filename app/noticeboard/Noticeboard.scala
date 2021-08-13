package noticeboard

case class Noticeboard(
    title: String,
    description: String,
    entries: List[NoticeboardEntry]
)
