import net.ruippeixotog.scalascraper.browser.Browser
import play.api.inject.bind
import staff.{StaffLocation, StaffService, StaffXmlProvider}

import scala.concurrent.Future
import scala.util.Try
import scala.util.control.NonFatal

final class StaffServiceSpec
    extends AsyncSpec
    with ApplicationSpec
    with FileSpec
    with BrowserSpec {

  private var fakeMaxResults: Future[Browser#DocumentType] =
    Future.failed(new Throwable("TODO"))

  private var fakeStuffs: Future[Browser#DocumentType] =
    Future.failed(new Throwable("TODO"))

  def updateMaxResultsWithFile(filename: String): Unit =
    fakeMaxResults = Future.fromTry(
      Try(file(filename))
    )

  def updateStaffsWithFile(filename: String): Unit =
    fakeStuffs = Future.fromTry(
      Try(file(filename))
    )

  class FakeXmlProvider extends StaffXmlProvider {

    override def maxResults(
        location: StaffLocation
    ): Future[Browser#DocumentType] = fakeMaxResults

    override def staffs(
        location: StaffLocation,
        batch: Int
    ): Future[Browser#DocumentType] = fakeStuffs
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
      updateMaxResultsWithFile("gm_maxResults.xml")

      service
        .fetchMaxResults(StaffLocation.Gummersbach)
        .map(_ shouldBe 275)
        .recover { case NonFatal(e) => fail(e) }
    }

    "fail while fetching max results if there are none" in {
      updateMaxResultsWithFile("gm_maxResults_bad.xml")

      service
        .fetchMaxResults(StaffLocation.Gummersbach)
        .map(n => fail(s"expected no maxResults, but was $n"))
        .recover { case NonFatal(e) =>
          e.getMessage shouldBe "can't find max results"
        }
    }
  }
}
