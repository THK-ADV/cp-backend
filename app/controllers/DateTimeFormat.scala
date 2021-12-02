package controllers

import ops.DateTimeFormatOps
import org.joda.time.DateTime
import play.api.libs.json.{Format, JsResult, JsString, JsValue}

trait DateTimeFormat {
  implicit val localDateFormat: Format[DateTime] = new Format[DateTime] {
    override def reads(json: JsValue): JsResult[DateTime] =
      json.validate[String].map(DateTime.parse(_, DateTimeFormatOps.isoDate))

    override def writes(o: DateTime): JsValue =
      JsString(DateTimeFormatOps.isoDate.print(o))
  }
}
