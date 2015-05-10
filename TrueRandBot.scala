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
                         message: String) = message match {
    case (new Regex(""".r d([0-9]+)""")(num) => println(num)
    case _ => println("d")
  }
}
