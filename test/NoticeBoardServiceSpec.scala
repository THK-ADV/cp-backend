import net.ruippeixotog.scalascraper.browser.Browser
import noticeboard.NoticeBoardFeed._
import noticeboard.{
  NoticeBoardDataProvider,
  NoticeBoardFeed,
  NoticeBoardService
}
import play.api.inject.bind

import scala.concurrent.Future
import scala.util.Try
import scala.util.control.NonFatal

class NoticeBoardServiceSpec
    extends AsyncSpec
    with ApplicationSpec
    with FileSpec
    with BrowserSpec {

  class FakeRSSProvider extends NoticeBoardDataProvider {
    override def noticeBoard(
        feed: NoticeBoardFeed
    ): Future[Browser#DocumentType] = {
      val filename = feed match {
        case Informatik              => "pinboard_inf.xml"
        case Ingenieurwissenschaften => "pinboard_ing.xml"
        case Maschinenbau | Wirtschaftsingenieurwesen | Produktdesign |
            Prozessentwicklung =>
          "pinboard_maschinenbau.xml"
        case Elektrotechnik | AutomationAndIT => "pinboard_etechnik.xml"
      }
      Future.fromTry(Try(file(filename)))
    }
  }

  override protected def bindings = Seq(
    bind(classOf[NoticeBoardDataProvider]).toInstance(new FakeRSSProvider())
  )
  val service = app.injector.instanceOf[NoticeBoardService]

  "A NoticeBoardService" should {
    "fetch notice board for 'Informatik'" in {
      service
        .fetchNoticeBoard(NoticeBoardFeed.Informatik)
        .map { b =>
          b.title shouldBe "Schwarzes Brett – Informatik"
          b.entries.size shouldBe 7
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch notice board for 'Grundstudium Ingenieurwissenschaften'" in {
      service
        .fetchNoticeBoard(NoticeBoardFeed.Ingenieurwissenschaften)
        .map { b =>
          b.title shouldBe "Schwarzes Brett - Ingenieurwissenschaften Grundstudium"
          b.entries.size shouldBe 16
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch notice board for 'Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung'" in {
      service
        .fetchNoticeBoard(NoticeBoardFeed.Maschinenbau)
        .map { b =>
          b.title shouldBe "Schwarzes Brett –  Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung"
          b.entries.size shouldBe 16
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch notice board for 'Elektrotechnik und Automation and IT'" in {
      service
        .fetchNoticeBoard(NoticeBoardFeed.Elektrotechnik)
        .map { b =>
          b.title shouldBe "Schwarzes Brett – Elektrotechnik und Automation and IT"
          b.entries.size shouldBe 20
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }
  }
}
