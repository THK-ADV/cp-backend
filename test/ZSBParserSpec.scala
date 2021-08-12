import zsb.{ZSBEntry, ZSBParser}

class ZSBParserSpec
    extends UnitSpec
    with ApplicationSpec
    with FileSpec
    with BrowserSpec {

  val parser = app.injector.instanceOf(classOf[ZSBParser])

  "A ZSB Parser" should {
    "parse the zsb feed" in {
      val entries = List(
        ZSBEntry(
          "Starthilfe für Erstsemester",
          "Wie komme ich an meine Studienbescheinigung? Welche weiteren Informations- und Beratungsangebote gibt es an TH Köln? Was ist eine MultiCa oder wie lese ich ein Modulhandbuch? Bei diesen Fragestellungen und weiteren Anliegen steht Ihnen die Zentrale Studienberatung als Ansprechpartner zur Verfügung.",
          "https://www.th-koeln.de/studium/starthilfe-fuer-erstsemester_77588.php",
          Some(
            "/mam/bilder/studium/beratung/fittosize__358_201_adb0fc356b1d15c3680f0e1b223e350d_tutoren_innen_begleiten_deinen_studienstart.jpg"
          )
        ),
        ZSBEntry(
          "Workshop - \"Erfolgreich studieren! Aber wie?\"",
          "Workshop für Studierende zur Studienorganisation. Du hast das Gefühl, dass Dein Studium effektiver laufen könnte? Du möchtest Dein Zeitmanagement, Deine Arbeits- und Lerntechniken und den Umgang mit Hindernissen besser bewältigen können? Dieser Workshop unterstützt Dich genau dabei.",
          "https://www.th-koeln.de/studium/erfolgreich-studieren---aber-wie_67840.php",
          Some(
            "/mam/bilder/studium/beratung/fittosize__358_201_6af38686497a5fecf98572a04b87570f_workshop__erfolgreich_studieren_-_aber_wie__.jpg"
          )
        ),
        ZSBEntry(
          "Die 360°-Beratung - gemeinsam zum nächsten Schritt",
          "Mit der aktuellen Studiensituation unzufrieden?",
          "https://www.th-koeln.de/studium/360-beratung_55185.php",
          Some(
            "/mam/bilder/studium/beratung/fittosize__358_201_e744058ff8eb960dc0290a581a0701c8_360deg_beratung_-_neue_wege_gehen.jpg"
          )
        ),
        ZSBEntry(
          "Stipendienberatung an der TH Köln",
          "Sie haben Fragen zum Thema Stipendium oder möchten mehr erfahren zur Stipendienförderungen an deutschen Hochschulen? Dann wenden Sie sich gerne jederzeit an die Zentrale Studienberatung! Oder vereinbaren Sie direkt per E-Mail einen Beratungstermin: stipendienberatung@th-koeln.de",
          "https://www.th-koeln.de/studium/stipendienberatung_63405.php",
          Some(
            "/mam/bilder/studium/bewerbung_zulassung/finanz/fittosize__358_201_3474a5e3114f508ef488f61da93a171a_stipendienberatung_istock-903911970.jpg"
          )
        ),
        ZSBEntry(
          "Reflexionszeiten zum erfolgreichen Studieren",
          "Nehmen Sie sich Zeit für sich und Ihre Gedanken.",
          "https://www.th-koeln.de/studium/reflexionszeiten---nimm-dir-einen-moment-fuer-dich-und-deine-gedanken_64411.php",
          Some(
            "/mam/bilder/studium/beratung/fittosize__358_201_12b4e17a497c2cbe89e6e81f111dc768_reflexionszeiten.jpg"
          )
        ),
        ZSBEntry(
          "Beratung bei psycho-sozialen Problemen",
          "Bei Problemen während des Studiums – egal ob es sich um Studienstress, persönliche Krisen oder einfach nur um den Bedarf handelt, \"mal mit jemandem zu reden\" – stehen den Studierenden der TH Köln verschiedene Angebote des AStA und des Kölner Studierendenwerks zur Beratung zur Verfügung.",
          "https://www.th-koeln.de/studium/beratung-bei-psycho-sozialen-problemen_64638.php",
          None
        ),
        ZSBEntry(
          "Schreibberatung",
          "Ob Hausarbeit, Abschlussarbeit oder Promotion - die Schreibberatung der TH Köln bietet Unterstützung in allen Fragen rund um das wissenschaftliche Schreiben. Das Angebot umfasst individuelle Einzelberatungen, ein Schreibcoaching in der Gruppe sowie Workshops und Online-Seminare zur Verbesserung Ihrer Schreibkompetenz. Entscheiden Sie selbst, was für Sie persönlich das Richtige ist!",
          "https://www.th-koeln.de/studium/schreibberatung_43697.php",
          Some(
            "/mam/bilder/studium/fittosize__358_201_bde6c78b5582a7bd12f38e59792b89b9_superschreibtag_08__2_.jpg"
          )
        ),
        ZSBEntry(
          "Online MatheHelpDesk – jeden Dienstag, Mittwoch und Donnerstag",
          "Mit so viel Mathematik im Studium hatten Sie nicht gerechnet? An jedem Dienstag, Mittwoch und Donnerstag unterstützt Sie der MatheHelpDesk bei mathematischen Problemstellungen. Kostenfrei und ohne Anmeldung.",
          "https://www.th-koeln.de/hochschule/mathehelpdesk_75749.php",
          None
        ),
        ZSBEntry(
          "Zweifel am Studium",
          "Es gibt immer Perspektiven - Weiter geht´s",
          "https://www.th-koeln.de/studium/zweifel-am-studium_46515.php",
          Some(
            "/mam/bilder/studium/beratung/fittosize__358_201_4ce5ebb3e93d205b95da16830718bee0_istock-frau_zweifelt.jpg"
          )
        ),
        ZSBEntry(
          "Studieren mit Familie – Family Matters",
          "Studieren mit Familie ist eine besondere Herausforderung. Die TH Köln unterstützt Sie beim Studium mit Kind oder pflegebedürftigen Angehörigen.",
          "https://www.th-koeln.de/studium/studieren-mit-kind_168.php",
          Some(
            "/mam/bilder/hochschule/profil/familienservice/2016_studieren-mit-kind.jpg"
          )
        ),
        ZSBEntry(
          "Ingenieur*in sein – Lehrer*in werden",
          "Wer gerne erklärt, begeistern kann, gerne kommuniziert, wer Technik studiert und wen das Lehramt interessiert, der kann sich jetzt jederzeit während des Studiums für eine neue kooperative Lehramtsausbildung entscheiden, um sich so den Weg in ein alternatives Berufsfeld zu ermöglichen.",
          "https://www.th-koeln.de/studium/ingenieurin-sein--lehrerin-werden_60576.php",
          Some(
            "/mam/bilder/studium/beratung/lehramt/fittosize__358_201_9094a3879eb7bd8a1e1a4da0c41d6a12_lehrerpult.jpg"
          )
        )
      )
      val res = parser.parse(file("zsb.html")).value
      res.title shouldBe "Angebote für Studierende"
      res.description shouldBe "Prüfungsangst? Keine Idee, was nach dem Abschluss kommen soll? Ich glaube das Studium passt nicht zu mir? - Die Zentrale Studienberatung bietet Hilfestellungen, Ideen und Lösungsvorschläge wie Hindernissen oder Phasen der Orientierungslosigkeit im Studium begegnet werden kann."
      res.entries.size shouldBe entries.size
      res.entries.zip(entries).foreach { case (lhs, rhs) =>
        lhs.title shouldBe rhs.title
        lhs.body shouldBe rhs.body
        lhs.detailUrl shouldBe rhs.detailUrl
        lhs.imgUrl shouldBe rhs.imgUrl
      }
    }
  }

}
