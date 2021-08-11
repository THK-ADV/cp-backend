import newsfeed.{NewsfeedEntry, NewsfeedParser}

class NewsfeedParserSpec
    extends UnitSpec
    with ApplicationSpec
    with FileSpec
    with BrowserSpec {
  val parser = app.injector.instanceOf(classOf[NewsfeedParser])

  "A Newsfeed Parser" should {
    "parse the newsfeed for 'F11'" in {
      val entries = List(
        NewsfeedEntry(
          "Abfall als erneuerbare Energiequelle nutzen",
          "Die Urbanisierung stellt die Stadt Bamako in Mali vor Herausforderungen: eine funktionierende Abfallwirtschaft muss bereitgestellt und eine sichere Energieversorgung gewährleistet werden. Im internationalen Verbundprojekt ERA-SOLMAB arbeitet die TH Köln gemeinsam mit Projektpartnern daher an Konzepten und Strategien für eine energetische Verwertung von Reststoffen.",
          "https://www.th-koeln.de/hochschule/abfall-als-erneuerbare-energiequelle-nutzen_86649.php",
          None
        ),
        NewsfeedEntry(
          "Eintrag von Mikroplastik in der Umwelt reduzieren",
          "Von Müllvorkommen in den Flusseinzugsgebieten bis in die Ozeane: Plastik in der Umwelt ist ein globales Problem. Trotz zahlreicher Aktivitäten und Ansätze ist das Wissen über die gesamte Plastik-Verschmutzung noch begrenzt. Um Wissenslücken zu schließen, hat die TH Köln im Rahmen des Verbundvorhabens MicBin das Sickerwasser der Deponie Leppe beprobt.",
          "https://www.th-koeln.de/hochschule/eintrag-von-mikroplastik-in-der-umwelt-reduzieren_86610.php",
          Some(
            "https://www.th-koeln.de/mam/bilder/hochschule/aktuelles/pm/2021/fittosize_358_201_d43053ddbf1dbffebd0aa791910591ee_1_deponie_leppe_am_standort_metabolon_costa_belibasakis_th_koln.jpg"
          )
        ),
        NewsfeedEntry(
          "Wie sollte ein hybrider Lernraum gestaltet sein?",
          "Forschungsprojekt an der TH Köln sammelt und bewertet bundesweit Beispiele für erfolgreiche Konzepte und entwickelt sie weiter – BMBF fördert das Vorhaben mit 1 Mio. Euro",
          "https://www.th-koeln.de/hochschule/wie-sollte-ein-hybrider-lernraum-gestaltet-sein_86571.php",
          Some(
            "https://www.th-koeln.de/mam/bilder/hochschule/aktuelles/nachrichten/f10/fittosize_358_201_3d124ca8a4b87784d4fb61ca7c0961eb_d61_2594.jpg"
          )
        ),
        NewsfeedEntry(
          "Hochschule des Monats (Juli 2021)",
          "2020 starteten wir mit einer neuen Rubrik - den Partnerhochschulen des Monats. Jeden Monat hat das Referat für Internationale Angelegenheiten Ihnen eine europäische sowie eine außereuropäische Partnerhochschule vorgestellt. 2021 setzen wir die Reihe fort und stellen im monatlichen Wechsel einen europäischen bzw. außereuropäischen Hochschulpartner vor.",
          "https://www.th-koeln.de/hochschule/hochschule-des-monats-juli-2021_86561.php",
          Some(
            "https://www.th-koeln.de/mam/bilder/internationales/logos/fittosize__358_201_38045e67bdc74ee8ba4613af2586540d_partnerhochschule_des_monats.jpg"
          )
        ),
        NewsfeedEntry(
          "TH Köln erfolgreich beim Bund-Länder-Programm „Künstliche Intelligenz in der Hochschulbildung“",
          "Das Verbundvorhaben „KI greifbar machen und begreifen: Technologie und Gesellschaft verbinden durch Gestaltung“ (KITeGG), bei dem die TH Köln neben vier weiteren Hochschulen beteiligt ist, hat eine Zusage für den Antrag beim Bund-Länder-Programm „Künstliche Intelligenz in der Hochschulbildung“ der Gemeinsamen Wissenschaftskonferenz (GWK) erhalten.",
          "https://www.th-koeln.de/hochschule/kuenstliche-intelligenz-in-der-hochschulbildung_86276.php",
          None
        ),
        NewsfeedEntry(
          "Nachgefragt bei Prof. Dr. Dr. Carolin Palmer",
          "Fakultät für Informatik und Ingenieurwissenschaften, Professur für Arbeits- und Organisationspsychologie",
          "https://www.th-koeln.de/hochschule/nachgefragt-bei-prof-dr-dr-carolin-palmer_86151.php",
          Some(
            "https://www.th-koeln.de/mam/bilder/hochschule/aktuelles/nachrichten/2021insideout/2021/fittosize_358_201_b7753e7f3911c54459854761005ddb71_caroline_palmer.jpg"
          )
        )
      )
      val res = parser.parse(file("newsfeed_f10.html"))
      res.size shouldBe 50

      res.zip(entries).foreach { case (lhs, rhs) =>
        lhs.title shouldBe lhs.title
        lhs.body shouldBe rhs.body
        lhs.detailUrl shouldBe rhs.detailUrl
        lhs.imgUrl shouldBe rhs.imgUrl
      }
    }
  }
}
