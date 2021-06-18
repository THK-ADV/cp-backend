package mensa

import controllers.LocalDateFormat
import org.joda.time.LocalDate
import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json._

sealed trait Meal {
  def name: String
  def nameAdditives: String
  def additives: List[Int]
  def allergens: List[Int]
}

object Meal {
  case class Main(
      name: String,
      nameAdditives: String,
      additives: List[Int],
      allergens: List[Int]
  ) extends Meal()

  case class Description(
      name: String,
      nameAdditives: String,
      additives: List[Int],
      allergens: List[Int]
  ) extends Meal()

  implicit val writesMealMain: Writes[Meal.Main] =
    Json.writes[Meal.Main]

  implicit val writesMealDesc: Writes[Meal.Description] =
    Json.writes[Meal.Description]
}

sealed trait Role {
  def label: String
}

object Role {
  case object Student extends Role {
    override val label = "student"
  }

  case object Employee extends Role {
    override val label = "employee"
  }

  case object Guest extends Role {
    override val label = "guest"
  }

  implicit val writes: Writes[Role] =
    Writes[Role](r => JsString(r.label))
}

case class Price(value: Option[Double], role: Role)

object Price {
  implicit val config: Aux[Json.MacroOptions] =
    JsonConfiguration(optionHandlers = OptionHandlers.WritesNull)

  implicit val writes: Writes[Price] = Json.writes[Price]
}

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
  implicit val writes: Writes[Item] = Json.writes[Item]
}

case class Menu(date: LocalDate, items: Seq[Item])

object Menu extends LocalDateFormat {
  implicit val writes: Writes[Menu] = Json.writes[Menu]
}
