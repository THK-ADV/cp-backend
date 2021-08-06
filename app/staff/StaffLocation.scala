package staff

sealed trait StaffLocation

object StaffLocation {
  case object Gummersbach extends StaffLocation
  case object Deutz extends StaffLocation
  case object Suedstadt extends StaffLocation
  case object Leverkusen extends StaffLocation
}
