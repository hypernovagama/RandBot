import org.jibble.pircbot._

object TrueRandBotMain {
  def main(args: Array[String]) {
    // Now start our bot up.
    val bot = new TrueRandBot

    // Enable debugging output.
    bot.setVerbose(true)

    // Connect to the IRC server.
    bot.connect("irc.ourirc.com", 6668)

    // Join the #pircbot channel.
    bot.joinChannel("#truerandbot")
  }
}