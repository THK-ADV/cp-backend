package zsb

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import javax.inject.{Inject, Singleton}

@Singleton
class ZSBParser @Inject() (val config: ZSBConfig) {

  def parse(doc: Browser#DocumentType): Either[String, ZSBFeed] =
    for {
      content <- (doc >?> element("div #content")).toRight(
        "expected div with #content"
      )
      article <- parseArticle(content)
    } yield {
      val entries = content >> elements("div .content-modules div")
      ZSBFeed(article._1, article._2, entries.flatMap(parseEntry).toList)
    }

  private def parseArticle(elem: Element): Either[String, (String, String)] =
    for {
      article <- (elem >?> element("div div article")).toRight(
        "expected article"
      )
      title <- (article >?> text("h1")).toRight(
        "expected h1 with title inside article"
      )
      description <- (article >?> text("div p")).toRight(
        "expected p with desc inside article"
      )
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
        body.dropRight(5), // drop _Mehr
        config.normalizeDetailUrl(detailUrl),
        imgUrl.map(config.normalizeImageUrl)
      )
    }
}
