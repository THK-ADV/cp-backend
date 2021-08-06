package mensa

import controllers.JsonNullWritable
import play.api.libs.json.{Json, Writes}

case class Price(value: Option[Double], role: Role)

object Price extends JsonNullWritable {
  implicit val writes: Writes[Price] = Json.writes[Price]
}
