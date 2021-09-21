package controllers.legacy

import controllers.legacy.LegacyMensaController.parseLegacyMenus
import controllers.{JsonHttpResponse, MensaController}
import mensa.{AdditiveType, Item, Menu}
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

object LegacyMensaController {

  private val mensaDays = List(
    ("mon", "Montag"),
    ("tue", "Dienstag"),
    ("wed", "Mittwoch"),
    ("thu", "Donnerstag"),
    ("fri", "Freitag"),
    ("sat", "Samstag")
  )

  private val emptyJsArray = JsArray.empty

  private def parseIds(list: List[AdditiveType]): List[Int] =
    list.map(_.asInstanceOf[AdditiveType.ID].id)

  private def toJson(item: Item): JsObject =
    Json.obj(
      "name" -> item.category,
      "meal" -> Json.obj(
        "name" -> item.meal.name,
        "sup" -> parseIds(item.meal.additives)
          .appendedAll(parseIds(item.meal.allergens))
      ),
      "descr" -> Json.obj(
        "desc" -> item.description.name,
        "sup" -> parseIds(item.description.additives)
          .appendedAll(parseIds(item.description.allergens))
      ),
      "img" -> JsNull,
      "name" -> item.category,
      "price" -> item.prices.map(a =>
        a.value.map(Json.toJson[Double]).getOrElse(JsNull)
      )
    )

  private def parseMenus(menus: Seq[Menu]) =
    mensaDays.foldLeft(Json.obj()) { case (json, day) =>
      val meals = menus
        .find(_.weekday == day._2)
        .map(_.items.map(toJson))
        .map(JsArray.apply) getOrElse emptyJsArray
      json + (day._1, meals)
    }

  import mensa.MensaService.sliceCurrentWeek

  def parseLegacyMenus(menus: Seq[Menu]) =
    parseMenus(sliceCurrentWeek(menus))
}

@Singleton
class LegacyMensaController @Inject() (
    cc: ControllerComponents,
    val mensaController: MensaController,
    implicit val ctx: ExecutionContext
) extends AbstractController(cc)
    with JsonHttpResponse {

  def mensa(id: String) = Action.async { implicit r =>
    mensaController
      .fetchMenu(id)
      .map(menus => Ok(parseLegacyMenus(menus)))
  }
}
