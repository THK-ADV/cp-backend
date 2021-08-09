package staff

sealed trait StaffLocation

object StaffLocation {
  case object Gummersbach extends StaffLocation
  case object Deutz extends StaffLocation
  case object Suedstadt extends StaffLocation
  case object Leverkusen extends StaffLocation

  def apply(str: String): Option[StaffLocation] = str match {
    case "gm"  => Some(StaffLocation.Gummersbach)
    case "dz"  => Some(StaffLocation.Deutz)
    case "st"  => Some(StaffLocation.Suedstadt)
    case "lev" => Some(StaffLocation.Leverkusen)
    case _     => None
  }

  def unapply(arg: StaffLocation): String = arg match {
    case Gummersbach => "gm"
    case Deutz       => "dz"
    case Suedstadt   => "st"
    case Leverkusen  => "lev"
  }

  def all(): List[StaffLocation] = List(
    Gummersbach,
    Deutz,
    Suedstadt,
    Leverkusen
  )
}
