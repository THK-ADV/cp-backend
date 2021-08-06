class XMLParserOpsSpec extends UnitSpec {
  import parser.XMLParserOps._

  "A XML Parser" should {
    "parse a double value successfully" in {
      val xml = <a>12,5</a>
      val res = parseDouble(xml)
      res.value shouldBe 12.5
    }

    "fail parsing a double value if it's empty" in {
      val xml = <a></a>
      val res = parseDouble(xml)
      res shouldBe None
    }

    "parse a list of strings from a comma separated string" in {
      val xml = <a>A, B, C</a>
      val res = parseStrings(xml)
      res shouldBe List("A", "B", "C")
    }

    "parse a single string from a comma separated string if there is only one value" in {
      val xml = <a>A</a>
      val res = parseStrings(xml)
      res shouldBe List("A")
    }

    "parse an empty list of strings from a comma separated string if there is no value" in {
      val xml = <a></a>
      val res = parseStrings(xml)
      res shouldBe Nil
    }

    "parse a list of ints from a comma separated string" in {
      val xml = <a>1, 2, 3</a>
      val res = parseInts(xml)
      res shouldBe List(1, 2, 3)
    }

    "skip invalid ints while parsing a list of ints from a comma separated string" in {
      val xml = <a>1, A, 3, 2,5</a>
      val res = parseInts(xml)
      res shouldBe List(1, 3)
    }

    "parse an empty list of ints from a comma separated string if there is no value" in {
      val xml = <a></a>
      val res = parseInts(xml)
      res shouldBe Nil
    }

    "parse a string" in {
      val xml = <a>A</a>
      val res = parseString(xml)
      res shouldBe "A"
    }

    "parse a string and remove leading and trailing whitespace is there is some" in {
      val xml = <a>  A  </a>
      val res = parseString(xml)
      res shouldBe "A"
    }

    "parse an empty string" in {
      val xml = <a></a>
      val res = parseString(xml)
      res shouldBe ""
    }
  }
}
