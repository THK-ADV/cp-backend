package controllers

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Provider, Singleton}

@Singleton
class IndexController @Inject() (
    cc: ControllerComponents,
    router: Provider[play.api.routing.Router]
) extends AbstractController(cc) {

  def index() = Action {
    Ok(
      Json.obj(
        "msg" -> "it works",
        "routes" -> router.get().documentation.map(t => (t._1, t._2))
      )
    )
  }
}
