package noticeboard

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import org.joda.time.format.DateTimeFormat

import java.util.Locale
import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton
class NoticeboardParser @Inject() (val config: NoticeboardConfig) {

  private val formatter = DateTimeFormat
    .forPattern("EEE, dd MMM yyyy HH:mm:ss Z")
    .withLocale(Locale.US)

  def parse(doc: Browser#DocumentType): Either[String, Noticeboard] =
    for {
      title <- (doc >?> text("channel title")).toRight("expected title")
      desc <- (doc >?> text("channel description")).toRight(
        "expected description"
      )
    } yield Noticeboard(
      removeCDATA(title).trim,
      removeCDATA(desc).trim,
      (doc >> elements("channel item"))
        .flatMap(parseEntry)
        .toList
    )

  def parseEntry(elem: Element): Option[NoticeboardEntry] =
    for {
      date <- Try(
        formatter.parseLocalDateTime(elem >> text("pubDate"))
      ).toOption
      title <- elem >?> text("title")
      desc <- elem >?> text("description")
      detailUrl <- elem >?> text("guid")
    } yield NoticeboardEntry(
      removeCDATA(title).trim,
      removeCDATA(desc).trim,
      detailUrl,
      date
    )

  private def removeCDATA(str: String): String =
    str.stripPrefix("<![CDATA[").stripSuffix("]]>")
}
