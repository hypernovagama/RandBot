package io.inflationaaron.truerandbot
import org.jibble.pircbot._
import org.apache.commons.math3.random._

class TrueRandBot extends PircBot {
  this.setName("TrueRandBot")
  this.setLogin("TrueRandBot")

  val mst = new MersenneTwister()

  override def onMessage(channel: String,
                         sender: String,
                         login: String,
                         hostname: String,
                         message: String): Unit = {
    val RollExpression = """^.r (?:([1-9]+\d*)#)?([1-9]\d*)?d([1-9]\d*)?((?:\+[1-9]\d*)*) ?([\p{P}\w\u2E80-\u9FFF]*)""".r
    val LuckExpression = """^.rp""".r

    log(message)
    val cleanMessage = Colors.removeFormattingAndColors(message)
    cleanMessage match {
      case RollExpression(num, dice, face, bonus, desc) => HaveRollResult(num, dice, face, bonus, desc, channel, sender)
      case LuckExpression() => HaveRpResult(channel, sender)
      case _ => log("None of My Business")
    }
  }

  override def onInvite(targetNick: String,
                        sourceNick: String,
                        sourceLogin: String,
                        sourceHostname: String,
                        channel: String): Unit = {
    this.joinChannel(channel)
    this.sendMessage(channel, s"${Colors.BOLD}${Colors.RED}${targetNick}被${sourceNick}从异界召唤而来！")
  }

  private def makeMultiString(sender: String, desc: String, result: Array[Array[Int]]): String ={
    val s1 = new StringBuilder(s"${Colors.BOLD}${Colors.DARK_GREEN}$sender${Colors.BLUE}" +
                               s"进行了${Colors.RED}${result.length}${Colors.BLUE}次${Colors.BROWN}" +
                               s"$desc${Colors.BLUE}检定 = ${Colors.RED}")
    for (i <- 0 until result.length) {
      s1 ++= s"${result(i).last} "
    }
    s1 ++= " "
    s1.toString()
  }

  private def makeString(sender: String, desc: String, result: Array[Int]): String = {
    val s1 = new StringBuilder(s"${Colors.BOLD}${Colors.DARK_GREEN}$sender${Colors.BLUE}" +
                               s"进行了${Colors.BROWN}$desc${Colors.BLUE}检定 = " +
                               result.reverse.tail.mkString(s"( ${Colors.RED}", " ", s"${Colors.BLUE} ) ") +
                               s"= ${Colors.RED}${result.last}")
    s1.toString()
  }

  private def HaveRollResult(num: String,
                        dice: String,
                        face: String,
                        bonus: String,
                        desc: String,
                        channel: String,
                        sender: String): Unit = {
    log(s"Num:$num Dice:$dice Face:$face Bonus:$bonus Desc:$desc")

    val bonusSum = s"0$bonus".split("""\+""").map(_.toInt).sum

    val roll = new Roll(1, 20, bonusSum, mst)

    roll.dice = Option(dice) match { case Some(d) => d.toInt; case None => roll.dice }
    roll.face = Option(face) match { case Some(f) => f.toInt; case None => roll.face }

    Option(num) match {
      case Some(n) if n.isInstanceOf[String] =>
        val result = new Array[Array[Int]](n.toInt)
        for (i <- 0 until n.toInt) {
          result(i) = roll.doRoll()
        }

        this.sendMessage(channel, makeMultiString(sender, desc, result))
      case _ => this.sendMessage(channel, makeString(sender, desc, roll.doRoll()))
    }
  }

  private def HaveRpResult(channel: String, sender: String): Unit = {
    this.sendMessage(channel, s"${Colors.BOLD}${Colors.DARK_GREEN}$sender" +
                              s"${Colors.BLUE}今天的人品是${Colors.RED}${mst.nextInt(100) % 100 + 1}")
  }
}
