package noticeboard

case class NoticeBoard(
    title: String,
    description: String,
    entries: List[NoticeBoardEntry]
)
