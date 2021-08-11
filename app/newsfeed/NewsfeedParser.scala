package newsfeed

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import javax.inject.{Inject, Singleton}

@Singleton
class NewsfeedParser @Inject() (val config: NewsfeedConfig) {

  def parse(doc: Browser#DocumentType): List[NewsfeedEntry] =
    (doc >?> element("div #filterlistresult"))
      .fold(List.empty[NewsfeedEntry])(div =>
        (div >> elements("li")).map(parseEntry).toList
      )

  def parseEntry(elem: Element): NewsfeedEntry =
    NewsfeedEntry(
      (elem >> text("h2")),
      parseBody(elem),
      elem >> attr("href")("a"),
      elem >?> attr("src")("img")
    )

  private def parseBody(elem: Element): String =
    (elem >> elements("p"))
      .find(p => !p.hasAttr("class"))
      .fold("")(_.text.dropRight(5)) // _Mehr
}
