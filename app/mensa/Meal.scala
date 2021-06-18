package mensa

import play.api.libs.json.{Json, Writes}

sealed trait Meal {
  def name: String
  def nameAdditives: String
  def additives: List[AdditiveType]
  def allergens: List[AdditiveType]
}

object Meal {
  case class Main(
      name: String,
      nameAdditives: String,
      additives: List[AdditiveType],
      allergens: List[AdditiveType]
  ) extends Meal()

  case class Description(
      name: String,
      nameAdditives: String,
      additives: List[AdditiveType],
      allergens: List[AdditiveType]
  ) extends Meal()

  implicit val writesMealMain: Writes[Meal.Main] =
    Json.writes[Meal.Main]

  implicit val writesMealDesc: Writes[Meal.Description] =
    Json.writes[Meal.Description]
}
