import com.google.inject.AbstractModule
import mensa.{MensaConfig, MensaDataProvider, MensaXMLProvider}
import newsfeed.{NewsfeedConfig, NewsfeedDataProvider, NewsfeedHTMLProvider}
import noticeboard.{NoticeBoardConfig, NoticeBoardDataProvider, NoticeBoardRSSFeedProvider}
import staff.{StaffConfig, StaffDataProvider, StaffHTMLProvider}

class Module extends AbstractModule {
  override def configure(): Unit = {
    super.configure()

    bind(classOf[StaffConfig])
      .toInstance(
        StaffConfig(
          "https://www.th-koeln.de/hochschule/personen_3850.php?location_de%5B%5D=Campus+",
          "https://www.th-koeln.de/filter_list_more_person.php?language=de&location_de[]=Campus+",
          "&document_type[]=person&target=%2Fhochschule%2Fpersonen_3850.php&resultlayout=standard&start=",
          "https://www.th-koeln.de"
        )
      )

    // all: https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml/kstw/
    bind(classOf[MensaConfig])
      .toInstance(
        MensaConfig(
          "https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml",
          "https://www.max-manager.de/daten-extern/sw-koeln/slsys-xml/kstw/legende.xml"
        )
      )

    bind(classOf[NoticeBoardConfig])
      .toInstance(
        NoticeBoardConfig(
          "https://www.th-koeln.de/rss/"
        )
      )

    bind(classOf[NewsfeedConfig])
      .toInstance(
        NewsfeedConfig(
          "https://www.th-koeln.de/hochschule/nachrichten_232.php?faculty_de[]=",
          "https://www.th-koeln.de/hochschule/nachrichten_232.php"
        )
      )

    bind(classOf[StaffDataProvider])
      .to(classOf[StaffHTMLProvider])
      .asEagerSingleton()

    bind(classOf[MensaDataProvider])
      .to(classOf[MensaXMLProvider])
      .asEagerSingleton()

    bind(classOf[NoticeBoardDataProvider])
      .to(classOf[NoticeBoardRSSFeedProvider])
      .asEagerSingleton()

    bind(classOf[NewsfeedDataProvider])
      .to(classOf[NewsfeedHTMLProvider])
      .asEagerSingleton()
  }
}
