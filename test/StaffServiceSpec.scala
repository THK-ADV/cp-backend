import net.ruippeixotog.scalascraper.browser.Browser
import play.api.inject.bind
import staff.{StaffLocation, StaffService, StaffHTMLProvider}

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

  def updateMaxResultsWithFile(filename: String): Unit =
    fakeMaxResults = Future.fromTry(
      Try(file(filename))
    )

  class FakeXmlProvider extends StaffHTMLProvider {

    override def maxResults(
        location: StaffLocation
    ): Future[Browser#DocumentType] = fakeMaxResults

    override def staffs(
        location: StaffLocation,
        batch: Int
    ): Future[Browser#DocumentType] = {
      val loc = location match {
        case StaffLocation.Gummersbach => "gm"
        case StaffLocation.Deutz       => "dz"
        case StaffLocation.Suedstadt   => "st"
        case StaffLocation.Leverkusen  => "lev"
      }
      Future.fromTry(Try(file(s"${loc}_persons_${batch}.html")))
    }
  }

  override protected def bindings = Seq(
    bind(classOf[StaffHTMLProvider]).toInstance(new FakeXmlProvider())
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
      val expected5 = List(0, 10, 20)
      service.steps(0, 25, 10) shouldBe expected5
      val expected6 = List(0, 10)
      service.steps(0, 15, 10) shouldBe expected6
      val expected7 = List(0, 10, 20)
      service.steps(0, 21, 10) shouldBe expected7
    }

    "fetch max results for gm" in {
      updateMaxResultsWithFile("gm_maxResults.html")

      service
        .fetchMaxResults(StaffLocation.Gummersbach)
        .map(_ shouldBe 275)
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fail while fetching max results for gm if there are none" in {
      updateMaxResultsWithFile("gm_maxResults_bad.html")

      service
        .fetchMaxResults(StaffLocation.Gummersbach)
        .map(n => fail(s"expected no maxResults, but was $n"))
        .recover { case NonFatal(e) =>
          e.getMessage shouldBe "can't find max results"
        }
    }

    "fetch 25 staff entries for gm" in {
      updateMaxResultsWithFile("gm_maxResults_25.html")

      service
        .fetchStaff(StaffLocation.Gummersbach)
        .map(_.size shouldBe 25)
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch 15 staff entries for dz" in {
      updateMaxResultsWithFile("dz_maxResults_15.html")

      service
        .fetchStaff(StaffLocation.Deutz)
        .map(_.size shouldBe 15)
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch 15 staff entries for st" in {
      updateMaxResultsWithFile("st_maxResults_13.html")

      service
        .fetchStaff(StaffLocation.Suedstadt)
        .map(_.size shouldBe 13)
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch 15 staff entries for lev" in {
      updateMaxResultsWithFile("lev_maxResults_21.html")

      service
        .fetchStaff(StaffLocation.Leverkusen)
        .map(_.size shouldBe 21)
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }
  }
}
