package mensa

import play.api.libs.json.{Json, Writes}

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
