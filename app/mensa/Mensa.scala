package mensa

sealed trait Mensa

object Mensa {
  case object Gummersbach extends Mensa
  case object Deutz extends Mensa
  case object Suedstadt extends Mensa
}
