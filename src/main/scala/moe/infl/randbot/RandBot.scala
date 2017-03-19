package moe.infl.randbot

import org.jibble.pircbot._
import org.apache.commons.math3.random._

import scala.collection.mutable

class RandBot extends PircBot {
  this.setName("RandBot")
  this.setLogin("RandBot")
  this.setVersion("RandBot V0.2 @ BNDS")

  val mst = new MersenneTwister()
  var TimeTree = new mutable.HashMap[String, Long]()

  override def onMessage(channel: String,
                         sender: String,
                         login: String,
                         hostname: String,
                         message: String): Unit = {
    val RollExpression = ("""^.r (?:([1-9]+\d*)#)?([1-9]\d*)?d([1-9]\d*)?((?:[-\+][1-9]\d*)*) """ +
      """?([\p{P}\w\u2E80-\u9FFF]*)""").r
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
    val ElementsToDrop = TimeTree.dropWhile(System.currentTimeMillis() - _._2 < 10 * 60 * 1000)
    ElementsToDrop.foreach { e => this.partChannel(e._1, "Timed Out") }
    this.TimeTree --= ElementsToDrop.keySet
    this.TimeTree += Tuple2(channel, System.currentTimeMillis())

    this.joinChannel(channel)
    this.sendMessage(channel, s"${Colors.BOLD}${Colors.RED}${targetNick}被${sourceNick}从异界召唤而来！")
  }

  private def makeMultiString(sender: String, desc: String, result: Array[Array[Int]]): String = {
    val s1 = new StringBuilder(s"${Colors.BOLD}${Colors.DARK_GREEN}$sender${Colors.BLUE}" +
      s"进行了${Colors.RED}${result.length}${Colors.BLUE}次${Colors.BROWN}" +
      s"$desc${Colors.BLUE}检定 = ${Colors.RED}")
    for (i <- result.indices) {
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

  private def sendError(channel: String): Unit = {
    this.sendAction(channel, s"表示需要你提供一个${Colors.BOLD}更强的${Colors.NORMAL}服务器才能完成你的任务！")
  }

  private def HaveRollResult(num: String,
                             dice: String,
                             face: String,
                             bonus: String,
                             desc: String,
                             channel: String,
                             sender: String): Unit = {
    log(s"Num:$num Dice:$dice Face:$face Bonus:$bonus Desc:$desc")

    val bonusSum = """([-\+]\d+)""".r.findAllIn(bonus).toArray.map(_.toInt).sum

    val roll = new Roll(1, 20, bonusSum, mst)

    roll.dice = Option(dice) match {
      case Some(d) if d.toInt <= 100 => d.toInt
      case Some(d) => sendError(channel); return
      case None => roll.dice
    }
    roll.face = Option(face) match {
      case Some(f) if f.toInt <= 100 => f.toInt
      case Some(f) => sendError(channel); return
      case None => roll.face
    }

    Option(num) match {
      case Some(n) if n.toInt <= 100 =>
        val result = new Array[Array[Int]](n.toInt)
        for (i <- 0 until n.toInt) {
          result(i) = roll.doRoll()
        }

        this.sendMessage(channel, makeMultiString(sender, desc, result))
      case Some(n) => sendError(channel)
      case _ => this.sendMessage(channel, makeString(sender, desc, roll.doRoll()))
    }
  }

  private def HaveRpResult(channel: String, sender: String): Unit = {
    this.sendMessage(channel, s"${Colors.BOLD}${Colors.DARK_GREEN}$sender" +
      s"${Colors.BLUE}今天的人品是${Colors.RED}${mst.nextInt(100) % 100 + 1}")
  }
}
