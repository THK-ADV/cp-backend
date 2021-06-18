package mensa

import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json.{Json, JsonConfiguration, OptionHandlers, Writes}

case class Price(value: Option[Double], role: Role)

object Price {
  implicit val config: Aux[Json.MacroOptions] =
    JsonConfiguration(optionHandlers = OptionHandlers.WritesNull)

  implicit val writes: Writes[Price] = Json.writes[Price]
}
