package noticeboard

sealed trait NoticeBoardFeed {
  val label: String
  val id: String = NoticeBoardFeed.unapply(this)
}

object NoticeBoardFeed {
  case object Informatik extends NoticeBoardFeed {
    override val label = "Informatik"
  }
  case object Ingenieurwissenschaften extends NoticeBoardFeed {
    override val label = "Ingenieurwissenschaften"
  }
  case object Maschinenbau extends NoticeBoardFeed {
    override val label = "Maschinenbau"
  }
  case object Wirtschaftsingenieurwesen extends NoticeBoardFeed {
    override val label = "Wirtschaftsingenieurwesen"
  }
  case object Produktdesign extends NoticeBoardFeed {
    override val label = "Produktdesign"
  }
  case object Prozessentwicklung extends NoticeBoardFeed {
    override val label = "Prozessentwicklung"
  }
  case object Elektrotechnik extends NoticeBoardFeed {
    override val label = "Elektrotechnik"
  }
  case object AutomationAndIT extends NoticeBoardFeed {
    override val label = "Automation and IT"
  }

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
