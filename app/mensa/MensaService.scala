package mensa

import mensa.AdditiveType.{Full, ID}
import mensa.legend.{Additive, LegendParser}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MensaService @Inject() (
    private val mensaProvider: MensaXMLProvider,
    private val mensaParser: MensaParser,
    private val legendParser: LegendParser,
    private implicit val ctx: ExecutionContext
) {

  def fetchMensa(mensa: MensaLocation): Future[Seq[Menu]] =
    mensaProvider
      .mensaMenu(mensa)
      .map(mensaParser.parseMenu)

  def fetchLegend(): Future[Seq[Additive]] =
    mensaProvider
      .legend()
      .map(legendParser.parseLegend)

  def fetchMensaWithLegend(mensa: MensaLocation): Future[Seq[Menu]] = {
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
}
