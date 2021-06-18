import mensa._
import org.joda.time.LocalDate

class MensaParserSpec extends UnitSpec {

  val baseUrl = "https://mensa_parser.spec"

  val service = new MensaParser(baseUrl)

  "A Mensa Parser" should {

    "parse a timestamp successfully" in {
      val xml = <a timestamp="1623967200"></a>
      val res = service.parseTimestamp(xml)
      res.value shouldBe LocalDate.parse("2021-06-18")
    }

    "fail parsing a timestamp if there is no attribute" in {
      val xml = <a></a>
      val res = service.parseTimestamp(xml)
      res shouldBe None
    }

    "fail parsing a timestamp if the timestamp is invalid" in {
      val xml = <a timestamp="invalid"></a>
      val res = service.parseTimestamp(xml)
      res shouldBe None
    }

    "parse a thumbnail image url successfully" in {
      val xml = <foto>fotos/foo.jpg</foto>
      val (thumbnail, _) = service.parseImageUrl(xml)
      thumbnail.value shouldBe s"$baseUrl/fotos/foo.jpg"
    }

    "fail parsing a thumbnail image url if there is no url" in {
      val xml = <foto></foto>
      val (thumbnail, _) = service.parseImageUrl(xml)
      thumbnail shouldBe None
    }

    "parse a full image url successfully if it's cropped" in {
      val xml = <foto>fotos/foo_r480_cropped.jpg</foto>
      val (_, full) = service.parseImageUrl(xml)
      full.value shouldBe s"$baseUrl/fotos/foo.jpg"
    }

    "fail parsing a full image url if it's not cropped" in {
      val xml = <foto>fotos/foo.jpg</foto>
      val (_, full) = service.parseImageUrl(xml)
      full shouldBe None
    }

    "parse a double value successfully" in {
      val xml = <a>12,5</a>
      val res = service.parseDouble(xml)
      res.value shouldBe 12.5
    }

    "fail parsing a double value if it's empty" in {
      val xml = <a></a>
      val res = service.parseDouble(xml)
      res shouldBe None
    }

    "parse a list of strings from a comma separated string" in {
      val xml = <a>A, B, C</a>
      val res = service.parseStrings(xml)
      res shouldBe List("A", "B", "C")
    }

    "parse a single string from a comma separated string if there is only one value" in {
      val xml = <a>A</a>
      val res = service.parseStrings(xml)
      res shouldBe List("A")
    }

    "parse an empty list of strings from a comma separated string if there is no value" in {
      val xml = <a></a>
      val res = service.parseStrings(xml)
      res shouldBe Nil
    }

    "parse a list of ints from a comma separated string" in {
      val xml = <a>1, 2, 3</a>
      val res = service.parseInts(xml)
      res shouldBe List(1, 2, 3)
    }

    "skip invalid ints while parsing a list of ints from a comma separated string" in {
      val xml = <a>1, A, 3, 2,5</a>
      val res = service.parseInts(xml)
      res shouldBe List(1, 3)
    }

    "parse an empty list of ints from a comma separated string if there is no value" in {
      val xml = <a></a>
      val res = service.parseInts(xml)
      res shouldBe Nil
    }

    "parse a string" in {
      val xml = <a>A</a>
      val res = service.parseString(xml)
      res shouldBe "A"
    }

    "parse a string and remove leading and trailing whitespace is there is some" in {
      val xml = <a>  A  </a>
      val res = service.parseString(xml)
      res shouldBe "A"
    }

    "parse an empty string" in {
      val xml = <a></a>
      val res = service.parseString(xml)
      res shouldBe ""
    }

    "parse 3 prices successfully" in {
      val xml =
        <root>
          <price1>1,5</price1>
          <price2>2,5</price2>
          <price3>3,5</price3>
        </root>
      val res = service.parsePrices(xml)
      val prices = Set(
        Price(Some(1.5), Role.Student),
        Price(Some(2.5), Role.Employee),
        Price(Some(3.5), Role.Guest)
      )
      res shouldBe prices
    }

    "always parse 3 prices even if there is no value" in {
      val xml =
        <root>
          <price1>-</price1>
          <price2>2,5</price2>
          <price3>-</price3>
        </root>
      val res = service.parsePrices(xml)
      val prices = Set(
        Price(None, Role.Student),
        Price(Some(2.5), Role.Employee),
        Price(None, Role.Guest)
      )
      res shouldBe prices
    }

    "always parse 3 prices even if nodes are missing" in {
      val xml =
        <root>
          <price1>1,5</price1>
        </root>
      val res = service.parsePrices(xml)
      val prices = Set(
        Price(Some(1.5), Role.Student),
        Price(None, Role.Employee),
        Price(None, Role.Guest)
      )
      res shouldBe prices
    }

    "parse a meal successfully" in {
      val xml =
        <root>
          <name>name</name>
          <name_zus>full name</name_zus>
          <additives>1, 2</additives>
          <allergens></allergens>
        </root>

      val res = service.parseMeal(xml, Meal.Main.apply)
      val meal = Meal.Main("name", "full name", List(1, 2), Nil)
      res shouldBe meal
    }

    "parse a description successfully" in {
      val xml =
        <root>
          <name>name desc</name>
          <name_zus>full name desc</name_zus>
          <additives>1, 2</additives>
          <allergens>3, 4</allergens>
        </root>

      val res = service.parseMeal(xml, Meal.Description.apply)
      val meal =
        Meal.Description("name desc", "full name desc", List(1, 2), List(3, 4))
      res shouldBe meal
    }

    "parse an item successfully" in {
      val xml =
        <root>
          <category>cat 1</category>
          <meal>
            <name>name</name>
            <name_zus>full name</name_zus>
            <additives>1, 2</additives>
            <allergens></allergens>
          </meal>
          <description>
            <name>name desc</name>
            <name_zus>full name desc</name_zus>
            <additives>1, 2</additives>
            <allergens>3, 4</allergens>
          </description>
          <foodicons>A, B</foodicons>
          <price1>1,5</price1>
          <price2>2,5</price2>
          <price3>-</price3>
          <foto>fotos/foo_r480_cropped.jpg</foto>
        </root>

      val res = service.parseItem(xml)
      val item = Item(
        "cat 1",
        Meal.Main("name", "full name", List(1, 2), Nil),
        Meal.Description("name desc", "full name desc", List(1, 2), List(3, 4)),
        List("A", "B"),
        Set(
          Price(Some(1.5), Role.Student),
          Price(Some(2.5), Role.Employee),
          Price(None, Role.Guest)
        ),
        Some(s"$baseUrl/fotos/foo_r480_cropped.jpg"),
        Some(s"$baseUrl/fotos/foo.jpg")
      )
      res shouldBe item
    }

    "parse a menu successfully" in {
      val xml =
        <root>
          <date timestamp="1623967200">
            <item>
              <category>cat 1</category>
              <meal>
                <name>name</name>
                <name_zus>full name</name_zus>
                <additives>1, 2</additives>
                <allergens></allergens>
              </meal>
              <description>
                <name>name desc</name>
                <name_zus>full name desc</name_zus>
                <additives>1, 2</additives>
                <allergens>3, 4</allergens>
              </description>
              <foodicons>A, B</foodicons>
              <price1>1,5</price1>
              <price2>2,5</price2>
              <price3>-</price3>
              <foto>fotos/foo_r480_cropped.jpg</foto>
            </item>
            <item>
              <category>cat 2</category>
              <meal>
                <name>name2</name>
                <name_zus>full name2</name_zus>
                <additives>1, 2, 3</additives>
                <allergens>1</allergens>
              </meal>
              <description>
                <name>name desc2</name>
                <name_zus>full name desc2</name_zus>
                <additives>1, 2, 3</additives>
                <allergens>3, 4, 5</allergens>
              </description>
              <foodicons></foodicons>
              <price1>1,5</price1>
              <price2>2,5</price2>
              <price3>-</price3>
              <foto></foto>
            </item>
          </date>
        </root>
      val res = service.parseMenu(xml)
      val item1 = Item(
        "cat 1",
        Meal.Main("name", "full name", List(1, 2), Nil),
        Meal.Description("name desc", "full name desc", List(1, 2), List(3, 4)),
        List("A", "B"),
        Set(
          Price(Some(1.5), Role.Student),
          Price(Some(2.5), Role.Employee),
          Price(None, Role.Guest)
        ),
        Some(s"$baseUrl/fotos/foo_r480_cropped.jpg"),
        Some(s"$baseUrl/fotos/foo.jpg")
      )
      val item2 = Item(
        "cat 2",
        Meal.Main("name2", "full name2", List(1, 2, 3), List(1)),
        Meal.Description(
          "name desc2",
          "full name desc2",
          List(1, 2, 3),
          List(3, 4, 5)
        ),
        Nil,
        Set(
          Price(Some(1.5), Role.Student),
          Price(Some(2.5), Role.Employee),
          Price(None, Role.Guest)
        ),
        None,
        None
      )
      val menu =
        Menu(LocalDate.parse("2021-06-18"), "Freitag", Seq(item1, item2))
      res shouldBe Seq(menu)
    }
  }
}
