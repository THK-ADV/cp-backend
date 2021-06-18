package mensa

import mensa.legend.Additive
import play.api.libs.json.{JsNumber, Json, Writes}

sealed trait AdditiveType

object AdditiveType {
  case class ID(id: Int) extends AdditiveType
  case class Full(a: Additive) extends AdditiveType

  implicit val writes: Writes[AdditiveType] = Writes.apply {
    case id: ID     => JsNumber(id.id)
    case full: Full => Json.writes[Additive].writes(full.a)
  }
}
