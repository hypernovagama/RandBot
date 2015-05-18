package io.inflationaaron.randbot

import org.jibble.pircbot._

object RandBotMain {
  def main(args: Array[String]) {
    // Now start our bot up.
    val bot = new RandBot

    // Enable debugging output.
    bot.setVerbose(true)

    // AutoNickChange
    bot.setAutoNickChange(true)

    // Encoding: UTF-8
    bot.setEncoding("UTF-8")

    // Connect to the IRC server.
    bot.connect("irc.ourirc.com", 6668)

    // Identify the bot
    bot.identify("!@#$TRUErandBOT%^&*")

    // Join the #pircbot channel.
    bot.joinChannel("#randbot")
  }
}
