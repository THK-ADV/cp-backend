package staff

import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class StaffService @Inject() (
    private val htmlProvider: StaffDataProvider,
    private val parser: StaffParser,
    private implicit val ctx: ExecutionContext
) {

  def fromCache(location: StaffLocation): Future[List[Staff]] = ???

  def writeCache(staff: List[Staff]): Unit = ???

  def fetchMaxResults(location: StaffLocation): Future[Int] =
    htmlProvider.maxResults(location).flatMap { xml =>
      parser
        .parseMaxResults(xml)
        .fold(Future.failed[Int](new Throwable("can't find max results")))(
          Future.successful
        )
    }

  def fetchStaff(location: StaffLocation): Future[List[Staff]] =
    for {
      max <- fetchMaxResults(location)
      steps = this.steps(0, max, 10)
      results <- Future.sequence(
        steps.map(s => htmlProvider.staffs(location, s))
      )
    } yield results.flatMap(parser.parseEntries)

  def steps(start: Int, end: Int, step: Int): List[Int] = {
    if (end == 0) return Nil
    val xs = ListBuffer.empty[Int]
    var i = start
    while (i < end) {
      xs += i
      i += step
    }
    xs.toList
  }
}
