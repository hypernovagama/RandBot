package moe.infl.randbot

import org.apache.commons.math3.random._

class Roll(var dice: Int, var face: Int, var bonus: Int, mst: MersenneTwister) {
  def doRoll(): Array[Int] = {
    val result = new Array[Int](dice + 1)
    for (i <- 0 until dice) result(i) = (mst.nextInt(face) % face) + 1
    result(dice) = result.sum + bonus
    result
  }
}
