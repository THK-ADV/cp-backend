package controllers

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Format, JsResult, JsString, JsValue}

trait LocalDateTimeFormat {
  val dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")

  implicit val localDateTimeFormat: Format[LocalDateTime] =
    new Format[LocalDateTime] {
      override def reads(json: JsValue): JsResult[LocalDateTime] =
        json.validate[String].map(LocalDateTime.parse(_, dateFormatter))

      override def writes(o: LocalDateTime): JsValue =
        JsString(o.toString(dateFormatter))
    }
}
