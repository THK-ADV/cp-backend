import mensa.AdditiveType.{Full, ID}
import mensa.legend.Additive
import mensa.{AdditiveType, MensaService, Menu}
import org.joda.time.LocalDate

class MensaServiceSpec extends UnitSpec with ApplicationSpec {

  val service = app.injector.instanceOf[MensaService]

  "A Mensa Service" should {
    "lift simple additives to full additives using a legend" in {
      val simple: List[AdditiveType] = List(ID(1), ID(2), ID(3))
      val legend = Seq(
        Additive("a", "aa", Some(1)),
        Additive("b", "bb", Some(2)),
        Additive("c", "cc", Some(3))
      )
      val res = service.liftWithLegend(simple, legend)
      val full: List[AdditiveType] = legend.map(Full.apply).toList

      res shouldBe full
    }

    "skip lifting additives if they are already with full information" in {
      val fullAlready: List[AdditiveType] = List(
        Full(Additive("a", "aa", Some(1))),
        Full(Additive("b", "bb", Some(2))),
        Full(Additive("c", "cc", Some(3)))
      )
      val legend = Seq.empty
      val res = service.liftWithLegend(fullAlready, legend)
      res shouldBe fullAlready
    }

    "fallback to a simple additive if no legend information is found" in {
      val simple: List[AdditiveType] = List(ID(1), ID(2), ID(3))
      val legend = Seq(
        Additive("a", "aa", Some(1))
      )
      val res = service.liftWithLegend(simple, legend)
      val full: List[AdditiveType] = List(
        Full(Additive("a", "aa", Some(1))),
        ID(2),
        ID(3)
      )

      res shouldBe full
    }

    "skip lifting simple additives if there is no legend" in {
      val simple: List[AdditiveType] = List(ID(1), ID(2), ID(3))
      val legend = Seq.empty
      val res = service.liftWithLegend(simple, legend)
      res shouldBe simple
    }

    "filter menus which are in a given week" in {
      def createMenu(date: String) =
        Menu(LocalDate.parse(date), "", Seq.empty)

      val menus = Seq(
        createMenu("2021-09-03"),
        createMenu("2021-09-04"),
        createMenu("2021-09-05"),
        createMenu("2021-09-06"),
        createMenu("2021-09-07"),
        createMenu("2021-09-08"),
        createMenu("2021-09-09"),
        createMenu("2021-09-10"),
        createMenu("2021-09-11"),
        createMenu("2021-09-12"),
        createMenu("2021-09-13"),
      )
      val res = MensaService.slice(
        menus,
        LocalDate.parse("2021-09-06"),
        LocalDate.parse("2021-09-11")
      )
      val week = List(
        LocalDate.parse("2021-09-06"),
        LocalDate.parse("2021-09-07"),
        LocalDate.parse("2021-09-08"),
        LocalDate.parse("2021-09-09"),
        LocalDate.parse("2021-09-10"),
        LocalDate.parse("2021-09-11"),
      )
      res.size shouldBe 6
      res.forall(m => week.contains(m.date)) shouldBe true
    }
  }
}
