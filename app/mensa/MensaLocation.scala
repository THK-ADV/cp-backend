package mensa

sealed trait MensaLocation {
  val label: String
  val id: String = MensaLocation.unapply(this)
}

object MensaLocation {
  case object Gummersbach extends MensaLocation {
    override val label = "Gummersbach"
  }
  case object Deutz extends MensaLocation {
    override val label = "Deutz"
  }
  case object Suedstadt extends MensaLocation {
    override val label = "Südstadt"
  }
  case object Claudiusstrasse extends MensaLocation {
    override val label = "Claudiusstraße"
  }
  case object BistroDeutz extends MensaLocation {
    override val label = "Bistro Deutz"
  }

  def apply(str: String): Option[MensaLocation] = str match {
    case "gm"  => Some(Gummersbach)
    case "dz"  => Some(Deutz)
    case "st"  => Some(Suedstadt)
    case "cs"  => Some(Claudiusstrasse)
    case "bdz" => Some(BistroDeutz)
    case _     => None
  }

  def unapply(loc: MensaLocation): String = loc match {
    case Gummersbach     => "gm"
    case Deutz           => "dz"
    case Suedstadt       => "st"
    case Claudiusstrasse => "cs"
    case BistroDeutz     => "bdz"
  }

  def all(): List[MensaLocation] = List(
    Gummersbach,
    Deutz,
    Suedstadt,
    Claudiusstrasse,
    BistroDeutz
  )
}
