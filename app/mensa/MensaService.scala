package mensa

import mensa.AdditiveType.{Full, ID}
import mensa.legend.{Additive, LegendParser}
import org.joda.time.{DateTimeConstants, LocalDate, LocalDateTime}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

object MensaService {
  def sliceCurrentWeek(menus: Seq[Menu]): Seq[Menu] = {
    val now = LocalDateTime.now
    val date = now.toLocalDate

    // slice to monday - friday
    // if today is friday 15:00pm, saturday or sunday, show next week
    // otherwise should monday - friday of this week
    /*    val (start, end) =
      if (
        now.getDayOfWeek >= DateTimeConstants.FRIDAY &&
        now.getDayOfWeek <= DateTimeConstants.SUNDAY &&
        now.toLocalTime.getHourOfDay >= 15
      )
        (
          date.withDayOfWeek(DateTimeConstants.MONDAY),
          date.withDayOfWeek(DateTimeConstants.SATURDAY)
        )
      else
        (
          ???,
          ???
        )*/
    val (start, end) = (
      date.withDayOfWeek(DateTimeConstants.MONDAY),
      date.withDayOfWeek(DateTimeConstants.SATURDAY)
    )
    slice(menus, start, end)
  }

  def slice(
      menus: Seq[Menu],
      start: LocalDate,
      end: LocalDate
  ): Seq[Menu] = {
    def isBetween(start: LocalDate, end: LocalDate)(date: LocalDate): Boolean =
      !date.isBefore(start) && !date.isAfter(end)
    val inRange = isBetween(start, end) _
    menus.filter(m => inRange(m.date))
  }
}

@Singleton
class MensaService @Inject() (
    private val mensaProvider: MensaDataProvider,
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
