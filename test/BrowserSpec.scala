import net.ruippeixotog.scalascraper.browser.JsoupBrowser

import java.io.File
import scala.language.implicitConversions
import scala.xml.Elem

trait BrowserSpec {
  val browser = JsoupBrowser()

  implicit def toDocumentType(node: Elem): browser.DocumentType =
    browser.parseString(node.toString)

  implicit def toDocumentType(file: File): browser.DocumentType =
    browser.parseFile(file)
}
