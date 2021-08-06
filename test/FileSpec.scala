import org.apache.commons.io.FileUtils
import org.scalatest.TryValues

import java.io.File
import scala.io.Codec
import scala.util.Try

trait FileSpec { self: TryValues =>

  private def file0(filename: String): Try[File] =
    Try(new File(s"test/res/$filename"))

  def file(filename: String): File =
    file0(filename).success.value

  def readTestResourceFile(filename: String): String =
    file0(filename)
      .map(f => FileUtils.readFileToString(f, Codec.UTF8.charSet))
      .success
      .value
}
