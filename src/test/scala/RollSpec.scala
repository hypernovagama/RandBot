import moe.infl.randbot.Roll
import org.scalatest._
import org.apache.commons.math3.random._

class RollSpec extends FlatSpec with Matchers {
  "roll" should "have tests" in {
    val roll = new Roll(2, 3, 5, new MersenneTwister)
    println(roll.doRoll)
  }
}
