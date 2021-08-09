package mensa

sealed trait MensaLocation

object MensaLocation {
  case object Gummersbach extends MensaLocation
  case object Deutz extends MensaLocation
  case object Suedstadt extends MensaLocation
  case object Claudiusstrasse extends MensaLocation
  case object BistroDeutz extends MensaLocation
}
