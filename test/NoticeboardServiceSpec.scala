import net.ruippeixotog.scalascraper.browser.Browser
import noticeboard.NoticeboardFeed._
import noticeboard.{
  NoticeboardDataProvider,
  NoticeboardFeed,
  NoticeboardService
}
import play.api.inject.bind

import scala.concurrent.Future
import scala.util.Try
import scala.util.control.NonFatal

class NoticeboardServiceSpec
    extends AsyncSpec
    with ApplicationSpec
    with FileSpec
    with BrowserSpec {

  class FakeRSSProvider extends NoticeboardDataProvider {
    override def noticeboard(
        feed: NoticeboardFeed
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
    bind(classOf[NoticeboardDataProvider]).toInstance(new FakeRSSProvider())
  )

  val service = app.injector.instanceOf[NoticeboardService]

  "A NoticeBoardService" should {
    "fetch noticeboard for 'Informatik'" in {
      service
        .fetchNoticeboard(NoticeboardFeed.Informatik)
        .map { b =>
          b.title shouldBe "Schwarzes Brett – Informatik"
          b.entries.size shouldBe 7
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch noticeboard for 'Grundstudium Ingenieurwissenschaften'" in {
      service
        .fetchNoticeboard(NoticeboardFeed.Ingenieurwissenschaften)
        .map { b =>
          b.title shouldBe "Schwarzes Brett - Ingenieurwissenschaften Grundstudium"
          b.entries.size shouldBe 16
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch noticeboard for 'Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung'" in {
      service
        .fetchNoticeboard(NoticeboardFeed.Maschinenbau)
        .map { b =>
          b.title shouldBe "Schwarzes Brett –  Maschinenbau, Wirtschaftsingenieurwesen, Produktdesign und Prozessentwicklung"
          b.entries.size shouldBe 16
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }

    "fetch noticeboard for 'Elektrotechnik und Automation and IT'" in {
      service
        .fetchNoticeboard(NoticeboardFeed.Elektrotechnik)
        .map { b =>
          b.title shouldBe "Schwarzes Brett – Elektrotechnik und Automation and IT"
          b.entries.size shouldBe 20
        }
        .recover { case NonFatal(e) => fail(s"expected result, but was $e") }
    }
  }
}
