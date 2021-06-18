package mensa

import controllers.LocalDateFormat
import org.joda.time.LocalDate
import play.api.libs.json._

case class Menu(date: LocalDate, weekday: String, items: Seq[Item])

object Menu extends LocalDateFormat {
  implicit val writes: Writes[Menu] = Json.writes[Menu]
}
