import controllers.{LocalDateFormat, LocalDateTimeFormat}
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{JsString, JsSuccess}

class LocalDateTimeFormatSpec
    extends UnitSpec
    with LocalDateTimeFormat
    with LocalDateFormat {

  "A LocalDateTime format" should {
    "convert LocalDateTime to JSON and vice versa" in {
      val date1 = LocalDateTime.now
        .withYear(2018)
        .withMonthOfYear(3)
        .withDayOfMonth(16)
        .withHourOfDay(19)
        .withMinuteOfHour(30)
        .withSecondOfMinute(0)
        .withMillisOfSecond(0)

      localDateTimeFormat.writes(date1) shouldBe JsString("2018-03-16T19:30:00")

      val date2 = LocalDateTime.now
        .withYear(2020)
        .withMonthOfYear(1)
        .withDayOfMonth(14)
        .withHourOfDay(22)
        .withMinuteOfHour(18)
        .withSecondOfMinute(0)
        .withMillisOfSecond(0)

      localDateTimeFormat.reads(
        JsString("2020-01-14T22:18:00")
      ) shouldBe JsSuccess(date2)
    }
  }

  "A LocalDate format" should {
    "convert LocalDate to JSON and vice versa" in {
      val date1 = LocalDate.now
        .withYear(2018)
        .withMonthOfYear(3)
        .withDayOfMonth(16)

      localDateFormat.writes(date1) shouldBe JsString("2018-03-16")

      val date2 = LocalDate.now
        .withYear(2020)
        .withMonthOfYear(1)
        .withDayOfMonth(14)

      localDateFormat.reads(
        JsString("2020-01-14")
      ) shouldBe JsSuccess(date2)
    }
  }
}
