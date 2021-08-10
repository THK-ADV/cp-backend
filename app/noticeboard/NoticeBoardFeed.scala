package noticeboard

sealed trait NoticeBoardFeed

object NoticeBoardFeed {
  case object Informatik extends NoticeBoardFeed
  case object Ingenieurwissenschaften extends NoticeBoardFeed
  case object Maschinenbau extends NoticeBoardFeed
  case object Wirtschaftsingenieurwesen extends NoticeBoardFeed
  case object Produktdesign extends NoticeBoardFeed
  case object Prozessentwicklung extends NoticeBoardFeed
  case object Elektrotechnik extends NoticeBoardFeed
  case object AutomationAndIT extends NoticeBoardFeed

  def apply(str: String): Option[NoticeBoardFeed] = str match {
    case "inf"  => Some(Informatik)
    case "ing"  => Some(Ingenieurwissenschaften)
    case "mb"   => Some(Maschinenbau)
    case "wing" => Some(Wirtschaftsingenieurwesen)
    case "pd"   => Some(Produktdesign)
    case "pe"   => Some(Prozessentwicklung)
    case "et"   => Some(Elektrotechnik)
    case "ait"  => Some(AutomationAndIT)
    case _      => None
  }

  def unapply(arg: NoticeBoardFeed): String = arg match {
    case Informatik                => "inf"
    case Ingenieurwissenschaften   => "ing"
    case Maschinenbau              => "mb"
    case Wirtschaftsingenieurwesen => "wing"
    case Produktdesign             => "pd"
    case Prozessentwicklung        => "pe"
    case Elektrotechnik            => "et"
    case AutomationAndIT           => "ait"
  }

  def all(): List[NoticeBoardFeed] = List(
    Informatik,
    Ingenieurwissenschaften,
    Maschinenbau,
    Wirtschaftsingenieurwesen,
    Produktdesign,
    Prozessentwicklung,
    Elektrotechnik,
    AutomationAndIT
  )
}
