import moe.infl.randbot.Roll
import org.scalatest._
import org.apache.commons.math3.random._

class RollSpec extends FlatSpec with Matchers {
  "roll" should "have tests" in {
    val roll = new Roll(2, 3, 5, new MersenneTwister)
    roll.dice should be (2)
    roll.face should be (3)
    roll.bonus should be (5)

    val result = roll.doRoll()
    result should have length 3
    result(0) should be <= 3
    result(0) should be >= 0

    result(2) should be <= 11
    result(2) should be >= 5
  }
}
