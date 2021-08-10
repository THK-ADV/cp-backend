import com.google.inject.AbstractModule
import mensa.{HttpMensaXMLProvider, MensaConfig, MensaXMLProvider}
import noticeboard.NoticeBoardConfig
import staff.{HttpStaffHTMLProvider, StaffConfig, StaffHTMLProvider}

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
        noticeboard.NoticeBoardConfig(
          "https://www.th-koeln.de/rss/"
        )
      )

    bind(classOf[StaffHTMLProvider])
      .to(classOf[HttpStaffHTMLProvider])
      .asEagerSingleton()

    bind(classOf[MensaXMLProvider])
      .to(classOf[HttpMensaXMLProvider])
      .asEagerSingleton()
  }
}
