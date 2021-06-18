package mensa

import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json.{Json, JsonConfiguration, OptionHandlers, Writes}

case class Item(
    category: String,
    meal: Meal.Main,
    description: Meal.Description,
    foodIcons: List[String],
    prices: Set[Price],
    thumbnailUrl: Option[String],
    fullUrl: Option[String]
)

object Item {
  implicit val config: Aux[Json.MacroOptions] =
    JsonConfiguration(optionHandlers = OptionHandlers.WritesNull)

  implicit val writes: Writes[Item] = Json.writes[Item]
}
