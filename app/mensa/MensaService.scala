package mensa

import mensa.AdditiveType.{Full, ID}
import mensa.legend.{Additive, LegendParser}
import play.api.libs.ws.WSClient

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class MensaService @Inject() (
    ws: WSClient,
    private implicit val ctx: ExecutionContext
) {

  private val baseUrl =
    "https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml"

  private val legendUrl =
    s"$baseUrl/kstw/legende.xml"

  private val mensaParser = new MensaParser(baseUrl)

  private val legendParser = new LegendParser()

  def fetchMensa(mensa: Mensa) =
    for {
      resp <- ws.url(url(mensa)).get()
      if resp.status == 200 & resp.contentType == "text/xml"
    } yield mensaParser.parseMenu(resp.xml)

  def fetchLegend() =
    for {
      resp <- ws.url(legendUrl).get()
      if resp.status == 200 & resp.contentType == "text/xml"
    } yield legendParser.parseLegend(resp.xml)

  def fetchMensaWithLegend(mensa: Mensa) = {
    for {
      menu <- fetchMensa(mensa)
      legend <- fetchLegend()
    } yield menu.map(m =>
      m.copy(items =
        m.items.map(i =>
          i.copy(
            meal = updateMeal(i.meal, legend),
            description = updateDesc(i.description, legend)
          )
        )
      )
    )
  }

  def liftWithLegend(
      additives: List[AdditiveType],
      legend: Seq[Additive]
  ): List[AdditiveType] =
    additives.map {
      case ID(id) =>
        legend
          .find(_.id.contains(id))
          .fold[AdditiveType](ID(id))(Full.apply)
      case a => a
    }

  def updateMeal(meal: Meal.Main, legend: Seq[Additive]) =
    meal.copy(
      additives = liftWithLegend(meal.additives, legend),
      allergens = liftWithLegend(meal.allergens, legend)
    )

  def updateDesc(desc: Meal.Description, legend: Seq[Additive]) =
    desc.copy(
      additives = liftWithLegend(desc.additives, legend),
      allergens = liftWithLegend(desc.allergens, legend)
    )

  private def filename(mensa: Mensa): String = mensa match {
    case Mensa.Gummersbach => "mensa_und_cafeteria_gummersbach_ml.xml"
    case Mensa.Deutz       => "mensa_und_cafeteria_deutz.xml"
    case Mensa.Suedstadt   => "mensa_und_cafeteria_suedstadt.xml"
  }

  private def url(mensa: Mensa): String =
    s"$baseUrl/kstw/${filename(mensa)}"
}
