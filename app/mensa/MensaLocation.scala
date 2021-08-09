package mensa

sealed trait MensaLocation

object MensaLocation {
  case object Gummersbach extends MensaLocation
  case object Deutz extends MensaLocation
  case object Suedstadt extends MensaLocation
  case object Claudiusstrasse extends MensaLocation
  case object BistroDeutz extends MensaLocation

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
