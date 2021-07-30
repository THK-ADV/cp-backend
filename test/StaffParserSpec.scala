import staff.StaffParser

class StaffParserSpec extends UnitSpec {

  val parser = new StaffParser()

  "A Staff Parser Spec" should {
    "fail parsing a single entry if there are no fields" in {
      val xml = <tr></tr>
      parser.parseEntry(xml).isEmpty shouldBe true
    }

    "parse a single entry with all fields" in {
      val xml =
        <tr>
          <td>
            <a href="/personen/foo.bar/">bar, Foo</a>
          </td>
          <td>
            <span class="tel">
              <img src="/img/icons/contact-telefon.svg" alt="Telefon" class="tel-icon" />
              +49 2261-8196-6349
            </span>
            <br/>
            <span class="email">
              <img src="/img/icons/contact-email.svg" alt="Email" class="email-icon"/>
              foo%40bar.baz
            </span>
          </td>
        </tr>
      val res = parser.parseEntry(xml).value
      res.name shouldBe "bar, Foo"
      res.detailUrl shouldBe "/personen/foo.bar/"
      res.tel.value shouldBe "+49 2261-8196-6349"
      res.email.value shouldBe "foo@bar.baz"
    }

    "parse a single entry only with tel" in {
      val xml =
        <tr>
          <td>
            <a href="/personen/foo.bar/">bar, Foo</a>
          </td>
          <td>
            <span class="tel">
              <img src="/img/icons/contact-telefon.svg" alt="Telefon" class="tel-icon" />
              +49 2261-8196-6349
            </span>
          </td>
        </tr>
      val res = parser.parseEntry(xml).value
      res.name shouldBe "bar, Foo"
      res.detailUrl shouldBe "/personen/foo.bar/"
      res.tel.value shouldBe "+49 2261-8196-6349"
      res.email.isEmpty shouldBe true
    }

    "parse a single entry where tel is empty" in {
      val xml =
        <tr>
          <td>
            <a href="/personen/foo.bar/">bar, Foo</a>
          </td>
          <td>
            <span class="tel">
              <img src="/img/icons/contact-telefon.svg" alt="Telefon" class="tel-icon" />
            </span>
          </td>
        </tr>
      val res = parser.parseEntry(xml).value
      res.name shouldBe "bar, Foo"
      res.detailUrl shouldBe "/personen/foo.bar/"
      res.tel.isEmpty shouldBe true
      res.email.isEmpty shouldBe true
    }

    "parse a single entry only with email" in {
      val xml =
        <tr>
          <td>
            <a href="/personen/foo.bar/">bar, Foo</a>
          </td>
          <td>
            <span class="email">
              <img src="/img/icons/contact-email.svg" alt="Email" class="email-icon"/>
              foo%40bar.baz
            </span>
          </td>
        </tr>
      val res = parser.parseEntry(xml).value
      res.name shouldBe "bar, Foo"
      res.detailUrl shouldBe "/personen/foo.bar/"
      res.tel.isEmpty shouldBe true
      res.email.value shouldBe "foo@bar.baz"
    }

    "parse a single entry where email is empty" in {
      val xml =
        <tr>
          <td>
            <a href="/personen/foo.bar/">bar, Foo</a>
          </td>
          <td>
            <span class="email">
              <img src="/img/icons/contact-email.svg" alt="Email" class="email-icon"/>
            </span>
          </td>
        </tr>
      val res = parser.parseEntry(xml).value
      res.name shouldBe "bar, Foo"
      res.detailUrl shouldBe "/personen/foo.bar/"
      res.tel.isEmpty shouldBe true
      res.email.isEmpty shouldBe true
    }

    "parse a single entry only with name" in {
      val xml =
        <tr>
          <td>
            <a href="/personen/foo.bar/">bar, Foo</a>
          </td>
        </tr>
      val res = parser.parseEntry(xml).value
      res.name shouldBe "bar, Foo"
      res.detailUrl shouldBe "/personen/foo.bar/"
      res.tel.isEmpty shouldBe true
      res.email.isEmpty shouldBe true
    }
  }
}
