import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.element
import zsb.ZSBDataProvider

import scala.util.control.NonFatal

class ZSBDataProviderSpec extends AsyncSpec with ApplicationSpec {

  val dataProvider = app.injector.instanceOf[ZSBDataProvider]

  "A ZSB Data Provider" should {
    "fetch the zsb feed" in {
      dataProvider
        .zsbFeed()
        .map { doc =>
          val content = doc >?> element("div #content")
          val entries = content >> elements("div .content-modules div h2")
          content.isDefined shouldBe true
          entries.get should not be empty
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }
  }
}
