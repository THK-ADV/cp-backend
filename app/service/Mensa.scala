package service

sealed trait Mensa

object Mensa {
  case object GM extends Mensa
  case object DZ extends Mensa
  case object SS extends Mensa
}
