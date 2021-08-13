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

  def parse(doc: Browser#DocumentType): Noticeboard =
    Noticeboard(
      removeCDATA(doc >> text("channel title")).trim,
      removeCDATA(doc >> text("channel description")).trim,
      (doc >> elements("channel item"))
        .flatMap(parseEntry)
        .toList
    )

  def parseEntry(elem: Element): Option[NoticeboardEntry] =
    Try(formatter.parseLocalDateTime(elem >> text("pubDate"))).map { date =>
      NoticeboardEntry(
        removeCDATA(elem >> text("title")).trim,
        removeCDATA(elem >> text("description")).trim,
        elem >> text("guid"),
        date
      )
    }.toOption

  private def removeCDATA(str: String): String =
    str.stripPrefix("<![CDATA[").stripSuffix("]]>")
}
