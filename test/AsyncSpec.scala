import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.{OptionValues, TryValues}

trait AsyncSpec
    extends AsyncWordSpec
    with should.Matchers
    with OptionValues
    with TryValues
