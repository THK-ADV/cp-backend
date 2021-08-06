package staff

import controllers.JsonNullWritable
import play.api.libs.json.{Json, Writes}

case class Staff(
    name: String,
    detailUrl: String,
    tel: Option[String],
    email: Option[String]
)

object Staff extends JsonNullWritable {
  implicit val writes: Writes[Staff] = Json.writes[Staff]
}
