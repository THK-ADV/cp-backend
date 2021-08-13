package staff

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import javax.inject.{Inject, Singleton}

@Singleton
final class StaffParser @Inject() (val config: StaffConfig) {

  def parseMaxResults(doc: Browser#DocumentType): Either[String, Int] =
    (doc >?> attr("data-maxresults")("a[id=filter_list_more]"))
      .flatMap(_.toIntOption)
      .toRight("expected data-maxresults")

  def parseEntries(doc: Browser#DocumentType): List[Staff] = {
    val personalInfos = doc >> elementList("body a")
    val contactInfos = (doc >> elementList("body span"))
      .map { e =>
        val tel = e >?> text(".tel")
        val mail = e >?> text(".email")
        whenNonEmpty(tel) orElse whenNonEmpty(mail)
      }
      .grouped(2)
      .map(xs => (xs.head, xs.last))
      .toList
    if (personalInfos.size != contactInfos.size) {
      return Nil
    }
    personalInfos
      .zip(contactInfos)
      .flatMap { case (p, (t, m)) =>
        parseName(p).map { case (n, d) =>
          Staff(n, d, t, m)
        }
      }
  }

  private def parseName(elem: Element): Option[(String, String)] =
    parseNonEmptyString(elem).zip(
      (elem >?> attr("href")).map(config.detailUrlPrefix + _)
    )

  private def parseNonEmptyString(elem: Element): Option[String] = {
    val text = elem.text
    Option.when(text.nonEmpty)(text)
  }

  private def whenNonEmpty(opt: Option[String]): Option[String] = opt match {
    case Some(value) if value.nonEmpty => Some(value)
    case _                             => None
  }
}
