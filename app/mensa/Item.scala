package mensa

import controllers.JsonNullWritable
import play.api.libs.json.{Json, Writes}

case class Item(
    category: String,
    meal: Meal.Main,
    description: Meal.Description,
    foodIcons: List[String],
    prices: Set[Price],
    thumbnailUrl: Option[String],
    fullUrl: Option[String]
)

object Item extends JsonNullWritable {
  implicit val writes: Writes[Item] = Json.writes[Item]
}
