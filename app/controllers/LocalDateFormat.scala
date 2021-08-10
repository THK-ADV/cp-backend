package controllers

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Format, JsResult, JsString, JsValue}

trait LocalDateFormat {
  val dateF = DateTimeFormat.forPattern("yyyy-MM-dd")

  implicit val localDateFormat: Format[LocalDate] = new Format[LocalDate] {
    override def reads(json: JsValue): JsResult[LocalDate] =
      json.validate[String].map(LocalDate.parse(_, dateF))

    override def writes(o: LocalDate): JsValue =
      JsString(o.toString(dateF))
  }
}
