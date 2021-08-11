package newsfeed

sealed trait Newsfeed {
  val label: String
  val id: String = Newsfeed.unapply(this)
}

object Newsfeed {
  case object General extends Newsfeed {
    override val label = "Allgemein TH Köln"
  }
  case object F01 extends Newsfeed {
    override val label = "Angewandte Sozialwissenschaften"
  }
  case object F02 extends Newsfeed {
    override val label = "Kulturwissenschaften"
  }
  case object F03 extends Newsfeed {
    override val label = "Informations- und Kommunikationswissenschaften"
  }
  case object F04 extends Newsfeed {
    override val label = "Wirtschafts- und Rechtswissenschaften"
  }
  case object F05 extends Newsfeed {
    override val label = "Architektur"
  }
  case object F06 extends Newsfeed {
    override val label = "Bauingenieurwesen und Umwelttechnik"
  }
  case object F07 extends Newsfeed {
    override val label = "Informations-, Medien- und Elektrotechnik"
  }
  case object F08 extends Newsfeed {
    override val label = "Fahrzeugsysteme und Produktion"
  }
  case object F09 extends Newsfeed {
    override val label = "Anlagen, Energie- und Maschinensysteme"
  }
  case object F10 extends Newsfeed {
    override val label = "Angewandte Naturwissenschaften"
  }
  case object F11 extends Newsfeed {
    override val label = "Informatik und Ingenieurwissenschaften"
  }
  case object F12 extends Newsfeed {
    override val label = "Raumentwicklung und Infrastruktursysteme"
  }

  def apply(str: String): Option[Newsfeed] = str.toLowerCase match {
    case "f01"     => Some(F01)
    case "f02"     => Some(F02)
    case "f03"     => Some(F03)
    case "f04"     => Some(F04)
    case "f05"     => Some(F05)
    case "f06"     => Some(F06)
    case "f07"     => Some(F07)
    case "f08"     => Some(F08)
    case "f09"     => Some(F09)
    case "f10"     => Some(F10)
    case "f11"     => Some(F11)
    case "f12"     => Some(F12)
    case "general" => Some(General)
    case _         => None
  }

  def unapply(arg: Newsfeed): String = arg match {
    case F01     => "f01"
    case F02     => "f02"
    case F03     => "f03"
    case F04     => "f04"
    case F05     => "f05"
    case F06     => "f06"
    case F07     => "f07"
    case F08     => "f08"
    case F09     => "f09"
    case F10     => "f10"
    case F11     => "f11"
    case F12     => "f12"
    case General => "general"
  }

  def all(): List[Newsfeed] = List(
    F01,
    F02,
    F03,
    F04,
    F05,
    F06,
    F07,
    F08,
    F09,
    F10,
    F11,
    F12,
    General
  )
}
