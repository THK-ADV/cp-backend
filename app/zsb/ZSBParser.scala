package zsb

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import javax.inject.{Inject, Singleton}

case class ZSBEntry(
    title: String,
    body: String,
    detailUrl: String,
    imgUrl: Option[String]
)

case class ZSBFeed(
    title: String,
    description: String,
    entries: List[ZSBEntry]
)

@Singleton
class ZSBParser @Inject() (val config: ZSBConfig) {

  def parse(doc: Browser#DocumentType): Option[ZSBFeed] =
    for {
      content <- doc >?> element("div #content")
      (title, desc) <- parseArticle(content)
    } yield {
      val entries = content >> elements("div .content-modules div")
      ZSBFeed(title, desc, entries.flatMap(parseEntry).toList)
    }

  private def parseArticle(elem: Element): Option[(String, String)] =
    for {
      article <- elem >?> element("div div article")
      title <- article >?> text("h1")
      description <- article >?> text("div p")
    } yield (title, description)

  private def parseEntry(elem: Element): Option[ZSBEntry] =
    for {
      title <- elem >?> text("h2")
      body <- elem >?> text("p")
      detailUrl <- elem >?> attr("href")("a")
    } yield {
      val imgUrl = elem >?> attr("src")("img")
      ZSBEntry(
        title,
        body.dropRight(5),
        config.normalizeDetailUrl(detailUrl),
        imgUrl
      ) // drop _Mehr
    }
}
