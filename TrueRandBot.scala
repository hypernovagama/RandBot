import org.jibble.pircbot._
import scala.util.matching._

class TrueRandBot extends PircBot {
  def TrueRandBot() {
    this.setName("TrueRandBot")
  }

  override def onMessage(channel: String,
                         sender: String,
                         login: String,
                         hostname: String,
                         message: String) = {
    val reg = new Regex("""^.r (?:([1-9]+)#)?([1-9]*)d([1-9]*)((?:\+[1-9]+)*) ([\p{P}\w\u2e80-\u9fff]*)""")
    message match {
      case reg(num, dice, face, bonus, desc) => println(num)
      case _ => println("d")
    }
  }
}
