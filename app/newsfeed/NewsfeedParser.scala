package newsfeed

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import javax.inject.{Inject, Singleton}

@Singleton
class NewsfeedParser @Inject() (val config: NewsfeedConfig) {

  def parse(doc: Browser#DocumentType): List[NewsfeedEntry] =
    (doc >> elementList("div #filterlistresult li"))
      .flatMap(parseEntry)

  def parseEntry(elem: Element): Option[NewsfeedEntry] =
    for {
      title <- elem >?> text("h2")
      body <- parseBody(elem)
      detailUrl <- elem >?> attr("href")("a")
    } yield {
      val imgUrl = elem >?> attr("src")("img")
      NewsfeedEntry(title, body, detailUrl, imgUrl)
    }

  private def parseBody(elem: Element): Option[String] =
    (elem >> elements("p"))
      .find(p => !p.hasAttr("class"))
      .map(_.text.dropRight(5)) // drop _Mehr
}
