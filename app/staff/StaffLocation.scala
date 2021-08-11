package staff

sealed trait StaffLocation {
  val label: String
  val id: String = StaffLocation.unapply(this)
}

object StaffLocation {
  case object Gummersbach extends StaffLocation {
    override val label = "Gummersbach"
  }
  case object Deutz extends StaffLocation {
    override val label = "Deutz"
  }
  case object Suedstadt extends StaffLocation {
    override val label = "Südstadt"
  }
  case object Leverkusen extends StaffLocation {
    override val label = "Leverkusen"
  }

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
