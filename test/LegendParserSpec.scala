import mensa.legend.{Additive, LegendParser}

class LegendParserSpec extends UnitSpec {

  val parser = new LegendParser()

  "A Legend Parser" should {
    "parse an item successfully" in {
      val xml = <item id="1">a</item>
      val res = parser.parseItem(xml)
      res shouldBe ("a", Some(1))
    }

    "parse an item with an optional id if the id could not be parsed" in {
      val xml = <item id="a">a</item>
      val res = parser.parseItem(xml)
      res shouldBe ("a", None)
    }

    "parse additives successfully" in {
      val xml =
        <root>
          <item id="1">a</item>
          <item id="2">b</item>
          <item id="3">c</item>
        </root>
      val res = parser.parseAdditives(xml)
      val additives = Seq(
        ("a", Some(1)),
        ("b", Some(2)),
        ("c", Some(3))
      )
      res shouldBe additives
    }

    "parse a simple legend" in {
      val xml =
        <root>
          <de>
            <additives>
              <item id="1">a</item>
              <item id="2">b</item>
              <item id="3">c</item>
            </additives>
          </de>
          <en>
            <additives>
              <item id="1">a2</item>
              <item id="2">b2</item>
              <item id="3">c2</item>
            </additives>
          </en>
        </root>
      val res = parser.parseLegend(xml)
      val additives = Seq(
        Additive("a", "a2", Some(1)),
        Additive("b", "b2", Some(2)),
        Additive("c", "c2", Some(3))
      )
      res shouldBe additives
    }

    "parse a complex legend" in {
      val xml =
        <root>
          <de>
            <additives>
              <item id="1">a</item>
              <item id="2">b</item>
              <item id="3">c</item>
            </additives>
            <allergens>
              <item id="10">aa</item>
              <item id="11">bb</item>
              <item id="12">cc</item>
            </allergens>
            <others>
              <item id="20">aaa</item>
              <item id="21">bbb</item>
              <item id="22">ccc</item>
            </others>
          </de>
          <en>
            <additives>
              <item id="1">a2</item>
              <item id="2">b2</item>
              <item id="3">c2</item>
            </additives>
            <allergens>
              <item id="10">aa2</item>
              <item id="11">bb2</item>
              <item id="12">cc2</item>
            </allergens>
            <others>
              <item id="20">aaa2</item>
              <item id="21">bbb2</item>
              <item id="22">ccc2</item>
            </others>
          </en>
        </root>
      val res = parser.parseLegend(xml)
      val additives = Seq(
        Additive("a", "a2", Some(1)),
        Additive("b", "b2", Some(2)),
        Additive("c", "c2", Some(3)),
        Additive("aa", "aa2", Some(10)),
        Additive("bb", "bb2", Some(11)),
        Additive("cc", "cc2", Some(12)),
        Additive("aaa", "aaa2", Some(20)),
        Additive("bbb", "bbb2", Some(21)),
        Additive("ccc", "ccc2", Some(22))
      )
      res shouldBe additives
    }

    "skip parsing if additives are unequally large" in {
      val xml =
        <root>
          <de>
            <additives>
              <item id="1">a</item>
              <item id="2">b</item>
            </additives>
          </de>
          <en>
            <additives>
              <item id="1">a2</item>
              <item id="2">b2</item>
              <item id="3">c2</item>
            </additives>
          </en>
        </root>
      val res = parser.parseLegend(xml)
      val additives = Seq.empty
      res shouldBe additives
    }

    "fill nulls if an id is not equal" in {
      val xml =
        <root>
          <de>
            <additives>
              <item id="1">a</item>
              <item id="a">b</item>
              <item id="3">c</item>
            </additives>
          </de>
          <en>
            <additives>
              <item id="1">a2</item>
              <item id="2">b2</item>
              <item id="3">c2</item>
            </additives>
          </en>
        </root>
      val res = parser.parseLegend(xml)
      val additives = Seq(
        Additive("a", "a2", Some(1)),
        Additive("b", "b2", None),
        Additive("c", "c2", Some(3))
      )
      res shouldBe additives
    }
  }
}
