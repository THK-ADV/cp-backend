package ops

import org.joda.time.format.DateTimeFormat

object DateTimeFormatOps {
  val isoDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
}
