package io.inflationaaron.truerandbot
import org.jibble.pircbot._
import org.apache.commons.math3.random._

class TrueRandBot extends PircBot {
  this.setName("TrueRandBot")

  override def onMessage(channel: String,
                         sender: String,
                         login: String,
                         hostname: String,
                         message: String) = {
    val reg = """^.r (?:([1-9]+\d*)#)?([1-9]\d*)?d([1-9]\d*)?((?:\+[1-9]\d*)*) ?([\p{P}\w\u2e80-\u9fff]*)""".r
    println(message)
    message match {
      case reg(num, dice, face, bonus, desc) => getResult(num, dice, face, bonus, desc)
      case _ => println(message)
    }
  }

  private def getResult(num: String,
                        dice: String,
                        face: String,
                        bonus: String,
                        desc: String) = {
    println(s"Num:$num Dice:$dice Face:$face Bonus:$bonus Desc:$desc")

    val bonusSum = s"0$bonus".split("""\+""").map(_.toInt).reduceLeft(_+_)

    val roll = new Roll(1, 20, bonusSum, new MersenneTwister)

    (Option(dice), Option(face)) match {
      case (Some(dice), Some(face)) => roll.dice = dice.toInt;roll.face = face.toInt
      case (Some(dice), None) => roll.dice = dice.toInt
      case (None, Some(face)) => roll.face = face.toInt
      case _ =>
    }

    Option(num) match {
      case Some(num) if num.isInstanceOf[String] => {
        var result = new Array[Array[Int]](num.toInt)
        for (i <- 0 until num.toInt) {
          result(i) = roll.doRoll
        }
      }
      case _ => println("haha")
    }
  }
}
