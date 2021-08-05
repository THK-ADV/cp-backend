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

    "parse the number of max results" in {
      val xml =
        <div>
        <table>
          <thead>
            <tr>
              <th>Name, Vorname</th>
              <th>Telefon / E-Mail</th>
            </tr>
          </thead>
          <tbody id="filterlistresult">
            <tr>
              <td>
                <a href="/personen/foo.bar/">bar, foo</a>
              </td>
            </tr>
          </tbody>
        </table>
          <div>
            <a href="#" id="filter_list_more" data-maxresults="278">
              Mehr Ergebnisse<span class="anchor"></span>
            </a>
          </div>
      </div>

      parser.parseMaxResults(xml).value shouldBe 278
    }

    "fail parsing the number of max results if there is none inside an a tag" in {
      val xml1 =
        <div>
          <table>
            <thead>
              <tr>
                <th>Name, Vorname</th>
                <th>Telefon / E-Mail</th>
              </tr>
            </thead>
            <tbody id="filterlistresult" data-maxresults="278">
              <tr>
                <td>
                  <a href="/personen/foo.bar/">bar, foo</a>
                </td>
              </tr>
            </tbody>
          </table>
          <div>
            <a href="#" id="filter_list_more">
              Mehr Ergebnisse<span class="anchor"></span>
            </a>
          </div>
        </div>
      val xml2 =
        <div>
          <table>
            <thead>
              <tr>
                <th>Name, Vorname</th>
                <th>Telefon / E-Mail</th>
              </tr>
            </thead>
            <tbody id="filterlistresult">
              <tr>
                <td>
                  <a href="/personen/foo.bar/">bar, foo</a>
                </td>
              </tr>
            </tbody>
          </table>
          <div>
            <a href="#" id="filter_list_more">
              Mehr Ergebnisse<span class="anchor"></span>
            </a>
          </div>
        </div>

      parser.parseMaxResults(xml1).isEmpty shouldBe true
      parser.parseMaxResults(xml2).isEmpty shouldBe true
    }
  }
}
