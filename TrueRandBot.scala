import org.jibble.pircbot._
import scala.util.matching._

class TrueRandBot extends PircBot {
  this.setName("TrueRandBot")

  override def onMessage(channel: String,
                         sender: String,
                         login: String,
                         hostname: String,
                         message: String) = {
    val reg = """^.r (?:([1-9]+[0-9]*)#)?([1-9]?[0-9]*)d([1-9]?[0-9]*)((?:\+[1-9]+)*) ?([\p{P}\w\u2e80-\u9fff]*)""".r
    println(message)
    message match {
      case reg(num, dice, face, bonus, desc) => println(s"Num:$num Dice:$dice Face:$face Bonus:$bonus Desc:$desc")
      case _ => println(message)
    }
  }
}
