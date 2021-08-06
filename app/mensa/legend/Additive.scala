package mensa.legend

import controllers.JsonNullWritable
import play.api.libs.json.{Json, Writes}

case class Additive(deLabel: String, enLabel: String, id: Option[Int])

object Additive extends JsonNullWritable {
  implicit val writes: Writes[Additive] = Json.writes[Additive]
}
