import staff.{Staff, StaffParser}

import scala.language.implicitConversions

class StaffParserSpec extends UnitSpec with FileSpec with BrowserSpec {

  val parser = new StaffParser()

  "A Staff Parser Spec" should {
    "parse multiple entries in real life" in {
      val staffs = parser.parseEntries(file("gm_persons_0.html"))
      val res = List(
        Staff(
          "Albayrak, Can Adam",
          "/personen/can_adam.albayrak/",
          None,
          Some("can_adam.albayrak@th-koeln.de")
        ),
        Staff(
          "Algorri Guzman, Maria Elena",
          "/personen/elena.algorri/",
          Some("+49 2261-8196-6349"),
          Some("elena.algorri@th-koeln.de")
        ),
        Staff(
          "Alken, Johannes",
          "/personen/johannes.alken/",
          None,
          Some("johannes.alken@th-koeln.de")
        ),
        Staff(
          "Alterauge, Markus Christopher",
          "/personen/markus.alterauge/",
          None,
          Some("markus.alterauge@th-koeln.de")
        ),
        Staff(
          "Altjohann, Uwe Martin",
          "/personen/uwe_martin.altjohann/",
          Some("+49 2261-8196-6215"),
          Some("uwe_martin.altjohann@th-koeln.de")
        ),
        Staff(
          "Anders, Denis",
          "/personen/denis.anders/",
          Some("+49 2261-8196-6372"),
          Some("denis.anders@th-koeln.de")
        ),
        Staff(
          "Averkamp, Christian",
          "/personen/christian.averkamp/",
          Some("+49 2261-8196-6465"),
          Some("christian.averkamp@th-koeln.de")
        ),
        Staff(
          "Aziz, Ahmad Tarik",
          "/personen/ahmad.aziz/",
          Some("+49 2261-8196-6334"),
          Some("ahmad.aziz@th-koeln.de")
        ),
        Staff(
          "Bader, Lukas",
          "/personen/lukas.bader/",
          Some("+49 2261-8196-6293"),
          Some("lukas.bader@th-koeln.de")
        ),
        Staff(
          "Bartnik, Roman",
          "/personen/roman.bartnik/",
          Some("+49 2261-8196-6253"),
          Some("roman.bartnik@th-koeln.de")
        )
      )

      staffs shouldBe res
    }

    "return no staff entries if the html is empty" in {
      val xml = <root></root>
      parser.parseEntries(xml).isEmpty shouldBe true
    }

    "parse the number of max results in real file" in {
      parser
        .parseMaxResults(file("gm_maxResults.html"))
        .value shouldBe 275
    }

    "parse the number of max results with fake data" in {
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

      parser
        .parseMaxResults(xml)
        .value shouldBe 278
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

      parser
        .parseMaxResults(xml1)
        .isEmpty shouldBe true
      parser
        .parseMaxResults(xml2)
        .isEmpty shouldBe true
    }
  }
}
