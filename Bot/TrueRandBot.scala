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
      case reg(num, dice, face, bonus, desc) => println { MakeRoll(num, dice, face, bonus, desc) }
      case _ => println(message)
    }
  }
  
  private def MakeRoll(num: Any, dice: Any, face: Any, bonus:String, desc:String) = {
    println(s"Num:$num Dice:$dice Face:$face Bonus:$bonus Desc:$desc")
    
    val bonusSum = s"0$bonus".split("""\+""").map(_.toInt).reduceLeft(_+_)
    println(dice.isInstanceOf[String])
    println(face.isInstanceOf[String])
    (dice, face) match {
      case (x: String, y: String) =>
      case (x: String, null) => 
      case (null, y: String) =>
      case (null, null) =>
    }
    
    num match {
      case num: Int => {
        var result = new Array[Int](num)
        for (i <- 0 until num) {
          //result(i) = roll(face, bonusSum)
        }
      }
      case _ => println("haha")
    }
  }
  
  private def roll(face: Int, bonusSum: Int) = {
    val random = new MersenneTwister()
    bonusSum + (random.nextInt(face) % face) + 1
  }
}
