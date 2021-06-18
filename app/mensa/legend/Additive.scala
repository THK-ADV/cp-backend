package mensa.legend

import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json.{Json, JsonConfiguration, OptionHandlers, Writes}

case class Additive(deLabel: String, enLabel: String, id: Option[Int])

object Additive {
  implicit val config: Aux[Json.MacroOptions] =
    JsonConfiguration(optionHandlers = OptionHandlers.WritesNull)

  implicit val writes: Writes[Additive] = Json.writes[Additive]
}
