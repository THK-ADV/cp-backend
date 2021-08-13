package noticeboard

sealed trait NoticeboardFeed {
  val label: String
  val id: String = NoticeboardFeed.unapply(this)
}

object NoticeboardFeed {
  case object Informatik extends NoticeboardFeed {
    override val label = "Informatik"
  }
  case object Ingenieurwissenschaften extends NoticeboardFeed {
    override val label = "Ingenieurwissenschaften"
  }
  case object Maschinenbau extends NoticeboardFeed {
    override val label = "Maschinenbau"
  }
  case object Wirtschaftsingenieurwesen extends NoticeboardFeed {
    override val label = "Wirtschaftsingenieurwesen"
  }
  case object Produktdesign extends NoticeboardFeed {
    override val label = "Produktdesign"
  }
  case object Prozessentwicklung extends NoticeboardFeed {
    override val label = "Prozessentwicklung"
  }
  case object Elektrotechnik extends NoticeboardFeed {
    override val label = "Elektrotechnik"
  }
  case object AutomationAndIT extends NoticeboardFeed {
    override val label = "Automation and IT"
  }

  def apply(str: String): Option[NoticeboardFeed] = str match {
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

  def unapply(arg: NoticeboardFeed): String = arg match {
    case Informatik                => "inf"
    case Ingenieurwissenschaften   => "ing"
    case Maschinenbau              => "mb"
    case Wirtschaftsingenieurwesen => "wing"
    case Produktdesign             => "pd"
    case Prozessentwicklung        => "pe"
    case Elektrotechnik            => "et"
    case AutomationAndIT           => "ait"
  }

  def all(): List[NoticeboardFeed] = List(
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
