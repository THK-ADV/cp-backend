import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.wordspec.AnyWordSpec

trait UnitSpec
    extends AnyWordSpec
    with should.Matchers
    with OptionValues
    with TryValues
