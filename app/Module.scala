import com.google.inject.AbstractModule
import staff.{HttpStaffXmlProvider, StaffConfig, StaffXmlProvider}

class Module extends AbstractModule {
  override def configure(): Unit = {
    super.configure()

    bind(classOf[StaffConfig])
      .toInstance(
        StaffConfig(
          "https://www.th-koeln.de/hochschule/personen_3850.php?location_de%5B%5D=Campus+",
          "https://www.th-koeln.de/filter_list_more_person.php?language=de&location_de[]=Campus+",
          "&document_type[]=person&target=%2Fhochschule%2Fpersonen_3850.php&resultlayout=standard&start="
        )
      )

    bind(classOf[StaffXmlProvider])
      .to(classOf[HttpStaffXmlProvider])
      .asEagerSingleton()
  }
}
