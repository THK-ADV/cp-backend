import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import play.api.inject.bind
import staff.{StaffLocation, StaffService, StaffXmlProvider}

import scala.concurrent.Future
import scala.util.Try

final class StaffServiceSpec
    extends AsyncSpec
    with ApplicationSpec
    with FileSpec
    with ScalaFutures {

  class FakeXmlProvider extends StaffXmlProvider {

    private val browser = JsoupBrowser()

    override def maxResults(location: StaffLocation): Future[Browser#DocumentType] =
      Future.fromTry(
        Try(browser.parseFile(file("gm_maxResults.xml")))
      )

    override def staffs(location: StaffLocation, batch: Int): Future[Browser#DocumentType] =
      Future.fromTry(
        Try(browser.parseString("<html></html>"))
      )
  }

  override protected def bindings = Seq(
    bind(classOf[StaffXmlProvider]).toInstance(new FakeXmlProvider())
  )

  val service = app.injector.instanceOf[StaffService]

  "A StaffService" should {
    "calculate steps" in {
      val expected1 = Nil
      service.steps(0, 0, 10) shouldBe expected1
      val expected2 = List(0, 10, 20, 30, 40, 50, 60, 70, 80)
      service.steps(0, 85, 10) shouldBe expected2
      val expected3 = List(0, 10, 20, 30, 40)
      service.steps(0, 49, 10) shouldBe expected3
      val expected4 = List(0, 10, 20, 30, 40, 50)
      service.steps(0, 52, 10) shouldBe expected4
    }

    "fetch max results" in {
      whenReady(
        service.fetchMaxResults(StaffLocation.Gummersbach),
        timeout(Span(5, Seconds))
      )(_ shouldBe 100)
      //service.fetchMaxResults(StaffLocation.Gummersbach).map(_ shouldBe 230)
    }

    "foo" in {
      import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
      import net.ruippeixotog.scalascraper.dsl.DSL._

      val browser = JsoupBrowser()
      val doc = browser.parseFile(file("gm_maxResults.xml"))
      (doc >?> element("#filter_list_more")).map(
        _.attr("data-maxresults")
      ) shouldBe Some(100)
    }
  }
}
