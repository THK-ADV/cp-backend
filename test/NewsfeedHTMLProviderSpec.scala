import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.{Element, ElementQuery}
import newsfeed.{Newsfeed, NewsfeedDataProvider}
import org.scalatest.Assertion

import scala.concurrent.Future
import scala.util.control.NonFatal

class NewsfeedHTMLProviderSpec extends AsyncSpec with ApplicationSpec {
  val dataProvider = app.injector.instanceOf(classOf[NewsfeedDataProvider])

  private def testDataProvider(
      newsfeed: Newsfeed
  )(f: ElementQuery[Element] => Assertion): Future[Assertion] =
    dataProvider
      .newsfeed(newsfeed)
      .map(doc =>
        f(
          (doc >?>
            element("div #filterlistresult") >>
            elements("li")).value
        )
      )
      .recover { case NonFatal(e) => fail(s"expected result, but was $e") }

  "A Newsfeed HTML Provider" should {
    "fetch the html newsfeed for 'F01'" in {
      testDataProvider(Newsfeed.F01)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F02'" in {
      testDataProvider(Newsfeed.F02)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F03'" in {
      testDataProvider(Newsfeed.F03)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F04'" in {
      testDataProvider(Newsfeed.F04)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F05'" in {
      testDataProvider(Newsfeed.F05)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F06'" in {
      testDataProvider(Newsfeed.F06)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F07'" in {
      testDataProvider(Newsfeed.F07)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F08'" in {
      testDataProvider(Newsfeed.F08)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F09'" in {
      testDataProvider(Newsfeed.F09)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F10'" in {
      testDataProvider(Newsfeed.F10)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F11'" in {
      testDataProvider(Newsfeed.F11)(_ should not be empty)
    }
    "fetch the html newsfeed for 'F12'" in {
      testDataProvider(Newsfeed.F12)(_ should not be empty)
    }
    "fetch the html newsfeed for 'General TH Köln'" in {
      testDataProvider(Newsfeed.General)(_ should not be empty)
    }
  }
}
