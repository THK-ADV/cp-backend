import noticeboard.{Noticeboard, NoticeboardEntry, NoticeboardParser}
import org.joda.time.LocalDateTime

class NoticeboardParserSpec
    extends UnitSpec
    with ApplicationSpec
    with FileSpec
    with BrowserSpec {

  val parser = app.injector.instanceOf(classOf[NoticeboardParser])

  "A Noticeboard Parser" should {
    "parse the noticeboard for 'Informatik'" in {
      val board = Noticeboard(
        "Schwarzes Brett – Informatik",
        "Aktuelle Informationen für die Studiengänge der Informatik",
        List(
          NoticeboardEntry(
            "Grundlagen des Web // mündliche Prüfung Seminarteil am 17.09.2021",
            "Grundlagen des Web // mündliche Prüfung Seminarteil am 17.09.2021",
            "https://www.th-koeln.de/hochschule/grundlagen-des-web--muendliche-pruefung-seminarteil-am-17092021_86453.php",
            LocalDateTime.parse("2021-07-23T10:03:00")
          ),
          NoticeboardEntry(
            "Kaffeebar wieder geöffnet",
            "und BAföG-Beratung vor Ort ab 15.07.21",
            "https://www.th-koeln.de/hochschule/kaffeebar-wieder-geoeffnet-und-bafoeg-beratung-vor-ort-ab-150721_86030.php",
            LocalDateTime.parse("2021-07-06T13:25:00")
          ),
          NoticeboardEntry(
            "QQ-Kurs „Hybride Lehre gestalten“",
            "Der Kurs ist ab sofort online; es gibt keine Teilnehmerbegrenzung",
            "https://www.th-koeln.de/hochschule/qq-kurs-hybride-lehre-gestalten-ab-sofort-online_85926.php",
            LocalDateTime.parse("2021-06-30T09:20:00")
          ),
          NoticeboardEntry(
            "Sonderregelung \"Zulassung zur Bachelorarbeit im Studiengang Wirtschaftsinformatik.....",
            "...bei ausstehender Prüfungsleistung „Betriebliche Anwendungssysteme I“ - Prof. Dr. Zapp",
            "https://www.th-koeln.de/hochschule/sonderregelung-zulassung-zur-bachelorarbeit-im-studiengang-wirtschaftsinformatik_85908.php",
            LocalDateTime.parse("2021-06-29T09:08:00")
          ),
          NoticeboardEntry(
            "Keine Prüfungen in AP 1 (Prof. Dr. Frank Victor) im nächsten Prüfungszeitraum ab 19.07.2021",
            "Im Prüfungszeitraum ab 19.07.2021 wird keine Klausur in AP 1 angeboten, sondern erst wieder im Prüfungszeitraum ab dem 20.09.2021.\n\nHärtefälle, die sich im letzten Semester  befinden und für die dies eine Verlängerung des Studiums bedeuten würde, möchten sich bitte per Email bei frank.victor (at) th-koeln.de melden.",
            "https://www.th-koeln.de/hochschule/keine-pruefungen-in-ap-1-prof-dr-frank-victor-im-naechsten-pruefungszeitraum-ab-19072021_83631.php",
            LocalDateTime.parse("2021-04-14T11:18:00")
          ),
          NoticeboardEntry(
            "(WPF) Steuern und Regeln in der Umwelttechnik",
            "WPF im SoSe 2021",
            "https://www.th-koeln.de/hochschule/wpf-steuern-und-regeln-in-der-umwelttechnik_83081.php",
            LocalDateTime.parse("2021-03-24T10:51:00")
          ),
          NoticeboardEntry(
            "Änderungen in der Modulprüfung Qualitätsmanagement",
            "ab dem Sommersemester 2020",
            "https://www.th-koeln.de/hochschule/aenderungen-in-der-modulpruefung-qualitaetsmanagement_71200.php",
            LocalDateTime.parse("2019-12-19T13:47:00")
          )
        )
      )
      val res = parser.parse(file("pinboard_inf.xml"))
      res.title shouldBe board.title
      res.description shouldBe board.description
      res.entries.size shouldBe board.entries.size
      res.entries.zip(board.entries).foreach { case (lhs, rhs) =>
        lhs.title shouldBe rhs.title
        lhs.description shouldBe rhs.description
        lhs.detailUrl shouldBe rhs.detailUrl
        lhs.published shouldBe rhs.published
      }
    }

    "parse the noticeboard for 'Grundstudium Ingenieurwissenschaften'" in {
      val board = Noticeboard(
        "Schwarzes Brett - Ingenieurwissenschaften Grundstudium",
        "Aktuelle Informationen für das Ingenieurwissenschaftliche Grundstudium",
        List(
          NoticeboardEntry(
            "WPF \"Interkulturelle Herausforderungen meistern\"",
            "Modul: Führungs- und Verhaltenskompetenzen, Ingenieurwissenschaften, Bachelor",
            "https://www.th-koeln.de/hochschule/herausforderungen-der-interkulturellen-zusammenarbeit-am-arbeitsplatz-meistern_86665.php",
            LocalDateTime.parse("2021-08-06T09:13:00")
          ),
          NoticeboardEntry(
            "Moderation/Präsentation/Rhetorik",
            "Seminarprogramm WS 2021/2022",
            "https://www.th-koeln.de/hochschule/moderatiopraesentationrhetorik_86574.php",
            LocalDateTime.parse("2021-07-30T11:22:00")
          ),
          NoticeboardEntry(
            "Kaffeebar wieder geöffnet",
            "und BAföG-Beratung vor Ort ab 15.07.21",
            "https://www.th-koeln.de/hochschule/kaffeebar-wieder-geoeffnet-und-bafoeg-beratung-vor-ort-ab-150721_86030.php",
            LocalDateTime.parse("2021-07-06T13:25:00")
          ),
          NoticeboardEntry(
            "Embedded Systems",
            "verbindliche Anmeldungen bis 07. April 2021",
            "https://www.th-koeln.de/hochschule/embedded-systems_83172.php",
            LocalDateTime.parse("2021-03-29T08:58:00")
          ),
          NoticeboardEntry(
            "(WPF) Steuern und Regeln in der Umwelttechnik",
            "WPF im SoSe 2021",
            "https://www.th-koeln.de/hochschule/wpf-steuern-und-regeln-in-der-umwelttechnik_83081.php",
            LocalDateTime.parse("2021-03-24T10:51:00")
          ),
          NoticeboardEntry(
            "E-Technik SS 21 /WS 21-22",
            "Übersichtsliste der Lehrveranstaltungen",
            "https://www.th-koeln.de/hochschule/lehrveranstaltungen-e-technik-ss-21-ws-21-22_82861.php",
            LocalDateTime.parse("2021-03-18T10:11:00")
          ),
          NoticeboardEntry(
            "Zoom Meeting",
            "Digitale Systeme/Embedded Systems",
            "https://www.th-koeln.de/hochschule/zoom-meeting_78982.php",
            LocalDateTime.parse("2020-11-05T18:23:00")
          ),
          NoticeboardEntry(
            "Fächerangebote Studiengang Elektronik",
            "Wintersemester 2020 / 2021",
            "https://www.th-koeln.de/hochschule/studiengang-elektronik_78369.php",
            LocalDateTime.parse("2020-10-22T11:08:00")
          ),
          NoticeboardEntry(
            "Herausforderungen der interkulturellen Zusammenarbeit meistern",
            "Modul: Führungs- und Verhaltenskompetenzen, Ingenieurwissenschaften, Bachelor",
            "https://www.th-koeln.de/hochschule/herausforderungen-der-interkulturellen-zusammenarbeit-meistern_77472.php",
            LocalDateTime.parse("2020-09-17T13:45:00")
          ),
          NoticeboardEntry(
            "Studienrichtung Elektronik",
            "Auslaufende Studienrichtung Elektronik",
            "https://www.th-koeln.de/hochschule/studienrichtung-elektronik_77266.php",
            LocalDateTime.parse("2020-09-10T12:20:00")
          ),
          NoticeboardEntry(
            "An die Studierenden der Studienrichtung Elektronik",
            "Elektronische Systeme",
            "https://www.th-koeln.de/hochschule/an-die-studierenden-der-studienrichtung-elektronik_76279.php",
            LocalDateTime.parse("2020-07-16T12:22:00")
          ),
          NoticeboardEntry(
            "Prüfungen zu Kursen aus dem WS19/20 bei Herrn Buchmann",
            "Studenten, die Prüfungen zu Kursen aus dem WS19/20 bei Herrn Buchmann ablegen möchten,\nmüssen sich zur Absprache der Form und des Termins per email bei Herrn Buchmann anmelden.\nDie Prüfung zu Bussysteme und Interfaces findet nicht am 21.7.  in der angekündigten Form statt.\nDie Prüfungen zu Kursen des Herrn Prof. Dr. Chr. Klein sind davon nicht betroffen.",
            "https://www.th-koeln.de/hochschule/pruefungen-zu-kursen-aus-dem-ws1920-bei-herrn-buchmann_75975.php",
            LocalDateTime.parse("2020-07-06T21:15:00")
          ),
          NoticeboardEntry(
            "Prüfungen - Prof. Klein",
            "Für Prüfungen bei Prof. Christoph Klein sollten sich Studierende direkt mit Prof. Klein in Verbindung setzen.",
            "https://www.th-koeln.de/hochschule/pruefungen---prof-klein_75567.php",
            LocalDateTime.parse("2020-06-18T09:10:00")
          ),
          NoticeboardEntry(
            "Kurs - Embedded Systems",
            "Alle Studenten/innen, die am Kurs Embedded Systems teilnehmen möchten, müssen sich bei Herrn Buchmann per email anmelden.\nDies ist notwendig, um einen Session Account zu versenden, da der Kurs mit dem Konferenz-Tool ZOOM gestartet wird.",
            "https://www.th-koeln.de/hochschule/kurs-embedded-systems_73707.php",
            LocalDateTime.parse("2020-03-27T10:59:00")
          ),
          NoticeboardEntry(
            "Studierende der Studienrichtung Elektronik im -aktuellen- vierten und fünften Semester",
            "Bitte melden Sie sich per Mail an Herrn Prof. Klein, christoph.klein@th-koeln.de",
            "https://www.th-koeln.de/hochschule/studierende-der-studienrichtung-elektronik-im--aktuellen--vierten-und-fuenften-semester_73546.php",
            LocalDateTime.parse("2020-03-21T16:36:00")
          ),
          NoticeboardEntry(
            "Änderungen in der Modulprüfung Qualitätsmanagement",
            "ab dem Sommersemester 2020",
            "https://www.th-koeln.de/hochschule/aenderungen-in-der-modulpruefung-qualitaetsmanagement_71200.php",
            LocalDateTime.parse("2019-12-19T13:47:00")
          )
        )
      )
      val res = parser.parse(file("pinboard_ing.xml"))
      res.title shouldBe board.title
      res.description shouldBe board.description
      res.entries.size shouldBe board.entries.size
      res.entries.zip(board.entries).foreach { case (lhs, rhs) =>
        lhs.title shouldBe rhs.title
        lhs.description shouldBe rhs.description
        lhs.detailUrl shouldBe rhs.detailUrl
        lhs.published shouldBe rhs.published
      }
    }

    "parse the noticeboard for 'Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung'" in {
      val board = Noticeboard(
        "Schwarzes Brett –  Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung",
        "Aktuelle Informationen für die Studiengänge: Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung",
        List(
          NoticeboardEntry(
            "WPF \"Interkulturelle Herausforderungen meistern\"",
            "Modul: Führungs- und Verhaltenskompetenzen, Ingenieurwissenschaften, Bachelor",
            "https://www.th-koeln.de/hochschule/herausforderungen-der-interkulturellen-zusammenarbeit-am-arbeitsplatz-meistern_86665.php",
            LocalDateTime.parse("2021-08-06T09:13:00")
          ),
          NoticeboardEntry(
            "Moderation/Präsentation/Rhetorik",
            "Seminarprogramm WS 2021/2022",
            "https://www.th-koeln.de/hochschule/moderatiopraesentationrhetorik_86574.php",
            LocalDateTime.parse("2021-07-30T11:22:00")
          ),
          NoticeboardEntry(
            "Hauptstudium Ingenieurwissenschaften - Anmeldung für teilnehmerbegrenzte Schwerpunktfächer im WS 21",
            "Hauptstudium Ingenieurwissenschaften - Anmeldung für teilnehmerbegrenzte Schwerpunktfächer im WS 21/21",
            "https://www.th-koeln.de/hochschule/hauptstudium-ingenieurwissenschaften---anmeldung-fuer-teilnehmerbegrenzte-schwerpunktfaecher-im-ws-2121_86416.php",
            LocalDateTime.parse("2021-07-21T12:30:00")
          ),
          NoticeboardEntry(
            "Kaffeebar wieder geöffnet",
            "und BAföG-Beratung vor Ort ab 15.07.21",
            "https://www.th-koeln.de/hochschule/kaffeebar-wieder-geoeffnet-und-bafoeg-beratung-vor-ort-ab-150721_86030.php",
            LocalDateTime.parse("2021-07-06T13:25:00")
          ),
          NoticeboardEntry(
            "WICHTIGE INFORMATION zur Fachprüfung in 'Kommunikation und Führung'- Frau Prof. Dr. G. Koeppe",
            "Frau Prof. Dr. G. Koeppe bietet letztmalig eine Fachprüfung in 'Kommunikation und Führung' an",
            "https://www.th-koeln.de/hochschule/wichtige-information-zur-fachpruefung-in-kommunikation-und-fuehrung--frau-prof-dr-g-koeppe_85380.php",
            LocalDateTime.parse("2021-06-13T07:36:00")
          ),
          NoticeboardEntry(
            "(WPF) Steuern und Regeln in der Umwelttechnik",
            "WPF im SoSe 2021",
            "https://www.th-koeln.de/hochschule/wpf-steuern-und-regeln-in-der-umwelttechnik_83081.php",
            LocalDateTime.parse("2021-03-24T10:51:00")
          ),
          NoticeboardEntry(
            "Wahlpflichtmodule im Sommersemester 2021 für den Bachelor Studiengang Elektrotechnik",
            "Bitte beachten Sie den Datei-Anhang.",
            "https://www.th-koeln.de/hochschule/wahlpflichtmodule-im-sommersemester-2021-fuer-den-bachelor-studiengang-elektrotechnik_81020.php",
            LocalDateTime.parse("2021-01-25T12:36:00")
          ),
          NoticeboardEntry(
            "Prüfungsangebot für den Master-Studiengang Wirtschaftsingenieurwesen zum Wintersemester 2020/21",
            "weitere Informationen finden Sie unter:",
            "https://www.th-koeln.de/hochschule/pruefungsangebot-fuer-den-master-studiengang-wirtschaftsingenieurwesen-zum-wintersemester-202021_78890.php",
            LocalDateTime.parse("2020-11-04T14:30:00")
          ),
          NoticeboardEntry(
            "Kurzfristiges Sonderangebot für Studierende der Ingenieurwissenschaften unserer Fakultät",
            "aufgrund sehr hoher Nachfrage unserer Sprachmodule wurden in Deutz Sonderkontingente eingeräumt, mit verkürzten Anmeldeverfahren.",
            "https://www.th-koeln.de/hochschule/kurzfristiges-sonderangebot-fuer-studierende-der-ingenieurwissenschaften-unserer-fakultaet_78883.php",
            LocalDateTime.parse("2020-11-04T13:52:00")
          ),
          NoticeboardEntry(
            "Praxissemesterinfoveranstaltung am 18.11.2020, 13:00h",
            "Die oben genannte Veranstaltung ist für alle Studierende der Ingenieurwissenschaften gedacht, die in naher Zukunft ein Praxissemester machen möchten und wird als ZOOM-Meeting stattfinden.",
            "https://www.th-koeln.de/hochschule/praxissemesterinfoveranstaltung-am-18112020-1300h_78748.php",
            LocalDateTime.parse("2020-11-02T14:33:00")
          ),
          NoticeboardEntry(
            "Fächerangebote Studiengang Elektronik",
            "Wintersemester 2020 / 2021",
            "https://www.th-koeln.de/hochschule/studiengang-elektronik_78369.php",
            LocalDateTime.parse("2020-10-22T11:08:00")
          ),
          NoticeboardEntry(
            "Herausforderungen der interkulturellen Zusammenarbeit meistern",
            "Modul: Führungs- und Verhaltenskompetenzen, Ingenieurwissenschaften, Bachelor",
            "https://www.th-koeln.de/hochschule/herausforderungen-der-interkulturellen-zusammenarbeit-meistern_77472.php",
            LocalDateTime.parse("2020-09-17T13:45:00")
          ),
          NoticeboardEntry(
            "An die Studierenden der Studienrichtung Elektronik",
            "Elektronische Systeme",
            "https://www.th-koeln.de/hochschule/an-die-studierenden-der-studienrichtung-elektronik_76279.php",
            LocalDateTime.parse("2020-07-16T12:22:00")
          ),
          NoticeboardEntry(
            "Prüfungen - Prof. Klein",
            "Für Prüfungen bei Prof. Christoph Klein sollten sich Studierende direkt mit Prof. Klein in Verbindung setzen.",
            "https://www.th-koeln.de/hochschule/pruefungen---prof-klein_75567.php",
            LocalDateTime.parse("2020-06-18T09:10:00")
          ),
          NoticeboardEntry(
            "Kurs - Embedded Systems",
            "Alle Studenten/innen, die am Kurs Embedded Systems teilnehmen möchten, müssen sich bei Herrn Buchmann per email anmelden.\nDies ist notwendig, um einen Session Account zu versenden, da der Kurs mit dem Konferenz-Tool ZOOM gestartet wird.",
            "https://www.th-koeln.de/hochschule/kurs-embedded-systems_73707.php",
            LocalDateTime.parse("2020-03-27T10:59:00")
          ),
          NoticeboardEntry(
            "Änderungen in der Modulprüfung Qualitätsmanagement",
            "ab dem Sommersemester 2020",
            "https://www.th-koeln.de/hochschule/aenderungen-in-der-modulpruefung-qualitaetsmanagement_71200.php",
            LocalDateTime.parse("2019-12-19T13:47:00")
          )
        )
      )
      val res = parser.parse(file("pinboard_maschinenbau.xml"))
      res.title shouldBe board.title
      res.description shouldBe board.description
      res.entries.size shouldBe board.entries.size
      res.entries.zip(board.entries).foreach { case (lhs, rhs) =>
        lhs.title shouldBe rhs.title
        lhs.description shouldBe rhs.description
        lhs.detailUrl shouldBe rhs.detailUrl
        lhs.published shouldBe rhs.published
      }
    }

    "parse the noticeboard for 'Elektrotechnik und Automation and IT'" in {
      val board = Noticeboard(
        "Schwarzes Brett – Elektrotechnik und Automation and IT",
        "Aktuelle Informationen für die Studiengänge Elektrotechnik und Automation and IT",
        List(
          NoticeboardEntry(
            "WPF \"Interkulturelle Herausforderungen meistern\"",
            "Modul: Führungs- und Verhaltenskompetenzen, Ingenieurwissenschaften, Bachelor",
            "https://www.th-koeln.de/hochschule/herausforderungen-der-interkulturellen-zusammenarbeit-am-arbeitsplatz-meistern_86665.php",
            LocalDateTime.parse("2021-08-06T09:13:00")
          ),
          NoticeboardEntry(
            "Moderation/Präsentation/Rhetorik",
            "Seminarprogramm WS 2021/2022",
            "https://www.th-koeln.de/hochschule/moderatiopraesentationrhetorik_86574.php",
            LocalDateTime.parse("2021-07-30T11:22:00")
          ),
          NoticeboardEntry(
            "Hauptstudium Ingenieurwissenschaften - Anmeldung für teilnehmerbegrenzte Schwerpunktfächer im WS 21",
            "Hauptstudium Ingenieurwissenschaften - Anmeldung für teilnehmerbegrenzte Schwerpunktfächer im WS 21/21",
            "https://www.th-koeln.de/hochschule/hauptstudium-ingenieurwissenschaften---anmeldung-fuer-teilnehmerbegrenzte-schwerpunktfaecher-im-ws-2121_86416.php",
            LocalDateTime.parse("2021-07-21T12:30:00")
          ),
          NoticeboardEntry(
            "Kaffeebar wieder geöffnet",
            "und BAföG-Beratung vor Ort ab 15.07.21",
            "https://www.th-koeln.de/hochschule/kaffeebar-wieder-geoeffnet-und-bafoeg-beratung-vor-ort-ab-150721_86030.php",
            LocalDateTime.parse("2021-07-06T13:25:00")
          ),
          NoticeboardEntry(
            "Embedded Systems",
            "verbindliche Anmeldungen bis 07. April 2021",
            "https://www.th-koeln.de/hochschule/embedded-systems_83172.php",
            LocalDateTime.parse("2021-03-29T08:58:00")
          ),
          NoticeboardEntry(
            "(WPF) Steuern und Regeln in der Umwelttechnik",
            "WPF im SoSe 2021",
            "https://www.th-koeln.de/hochschule/wpf-steuern-und-regeln-in-der-umwelttechnik_83081.php",
            LocalDateTime.parse("2021-03-24T10:51:00")
          ),
          NoticeboardEntry(
            "E-Technik SS 21 /WS 21-22",
            "Übersichtsliste der Lehrveranstaltungen",
            "https://www.th-koeln.de/hochschule/lehrveranstaltungen-e-technik-ss-21-ws-21-22_82861.php",
            LocalDateTime.parse("2021-03-18T10:11:00")
          ),
          NoticeboardEntry(
            "Wahlpflichtmodule im Sommersemester 2021 für den Bachelor Studiengang Elektrotechnik",
            "Bitte beachten Sie den Datei-Anhang.",
            "https://www.th-koeln.de/hochschule/wahlpflichtmodule-im-sommersemester-2021-fuer-den-bachelor-studiengang-elektrotechnik_81020.php",
            LocalDateTime.parse("2021-01-25T12:36:00")
          ),
          NoticeboardEntry(
            "Zoom Meeting",
            "Digitale Systeme/Embedded Systems",
            "https://www.th-koeln.de/hochschule/zoom-meeting_78982.php",
            LocalDateTime.parse("2020-11-05T18:23:00")
          ),
          NoticeboardEntry(
            "Kurzfristiges Sonderangebot für Studierende der Ingenieurwissenschaften unserer Fakultät",
            "aufgrund sehr hoher Nachfrage unserer Sprachmodule wurden in Deutz Sonderkontingente eingeräumt, mit verkürzten Anmeldeverfahren.",
            "https://www.th-koeln.de/hochschule/kurzfristiges-sonderangebot-fuer-studierende-der-ingenieurwissenschaften-unserer-fakultaet_78883.php",
            LocalDateTime.parse("2020-11-04T13:52:00")
          ),
          NoticeboardEntry(
            "Praxissemesterinfoveranstaltung am 18.11.2020, 13:00h",
            "Die oben genannte Veranstaltung ist für alle Studierende der Ingenieurwissenschaften gedacht, die in naher Zukunft ein Praxissemester machen möchten und wird als ZOOM-Meeting stattfinden.",
            "https://www.th-koeln.de/hochschule/praxissemesterinfoveranstaltung-am-18112020-1300h_78748.php",
            LocalDateTime.parse("2020-11-02T14:33:00")
          ),
          NoticeboardEntry(
            "Fächerangebote Studiengang Elektronik",
            "Wintersemester 2020 / 2021",
            "https://www.th-koeln.de/hochschule/studiengang-elektronik_78369.php",
            LocalDateTime.parse("2020-10-22T11:08:00")
          ),
          NoticeboardEntry(
            "Herausforderungen der interkulturellen Zusammenarbeit meistern",
            "Modul: Führungs- und Verhaltenskompetenzen, Ingenieurwissenschaften, Bachelor",
            "https://www.th-koeln.de/hochschule/herausforderungen-der-interkulturellen-zusammenarbeit-meistern_77472.php",
            LocalDateTime.parse("2020-09-17T13:45:00")
          ),
          NoticeboardEntry(
            "Studienrichtung Elektronik",
            "Auslaufende Studienrichtung Elektronik",
            "https://www.th-koeln.de/hochschule/studienrichtung-elektronik_77266.php",
            LocalDateTime.parse("2020-09-10T12:20:00")
          ),
          NoticeboardEntry(
            "An die Studierenden der Studienrichtung Elektronik",
            "Elektronische Systeme",
            "https://www.th-koeln.de/hochschule/an-die-studierenden-der-studienrichtung-elektronik_76279.php",
            LocalDateTime.parse("2020-07-16T12:22:00")
          ),
          NoticeboardEntry(
            "Prüfungen zu Kursen aus dem WS19/20 bei Herrn Buchmann",
            "Studenten, die Prüfungen zu Kursen aus dem WS19/20 bei Herrn Buchmann ablegen möchten,\nmüssen sich zur Absprache der Form und des Termins per email bei Herrn Buchmann anmelden.\nDie Prüfung zu Bussysteme und Interfaces findet nicht am 21.7.  in der angekündigten Form statt.\nDie Prüfungen zu Kursen des Herrn Prof. Dr. Chr. Klein sind davon nicht betroffen.",
            "https://www.th-koeln.de/hochschule/pruefungen-zu-kursen-aus-dem-ws1920-bei-herrn-buchmann_75975.php",
            LocalDateTime.parse("2020-07-06T21:15:00")
          ),
          NoticeboardEntry(
            "Prüfungen - Prof. Klein",
            "Für Prüfungen bei Prof. Christoph Klein sollten sich Studierende direkt mit Prof. Klein in Verbindung setzen.",
            "https://www.th-koeln.de/hochschule/pruefungen---prof-klein_75567.php",
            LocalDateTime.parse("2020-06-18T09:10:00")
          ),
          NoticeboardEntry(
            "Kurs - Embedded Systems",
            "Alle Studenten/innen, die am Kurs Embedded Systems teilnehmen möchten, müssen sich bei Herrn Buchmann per email anmelden.\nDies ist notwendig, um einen Session Account zu versenden, da der Kurs mit dem Konferenz-Tool ZOOM gestartet wird.",
            "https://www.th-koeln.de/hochschule/kurs-embedded-systems_73707.php",
            LocalDateTime.parse("2020-03-27T10:59:00")
          ),
          NoticeboardEntry(
            "Studierende der Studienrichtung Elektronik im -aktuellen- vierten und fünften Semester",
            "Bitte melden Sie sich per Mail an Herrn Prof. Klein, christoph.klein@th-koeln.de",
            "https://www.th-koeln.de/hochschule/studierende-der-studienrichtung-elektronik-im--aktuellen--vierten-und-fuenften-semester_73546.php",
            LocalDateTime.parse("2020-03-21T16:36:00")
          ),
          NoticeboardEntry(
            "Änderungen in der Modulprüfung Qualitätsmanagement",
            "ab dem Sommersemester 2020",
            "https://www.th-koeln.de/hochschule/aenderungen-in-der-modulpruefung-qualitaetsmanagement_71200.php",
            LocalDateTime.parse("2019-12-19T13:47:00")
          )
        )
      )
      val res = parser.parse(file("pinboard_etechnik.xml"))
      res.title shouldBe board.title
      res.description shouldBe board.description
      res.entries.size shouldBe board.entries.size
      res.entries.zip(board.entries).foreach { case (lhs, rhs) =>
        lhs.title shouldBe rhs.title
        lhs.description shouldBe rhs.description
        lhs.detailUrl shouldBe rhs.detailUrl
        lhs.published shouldBe rhs.published
      }
    }
  }
}
