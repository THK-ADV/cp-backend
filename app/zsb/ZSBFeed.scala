package zsb

case class ZSBFeed(
    title: String,
    description: String,
    entries: List[ZSBEntry]
)
