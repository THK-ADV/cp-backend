import mensa.AdditiveType.{Full, ID}
import mensa.legend.Additive
import mensa.{AdditiveType, MensaService}

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
  }
}
