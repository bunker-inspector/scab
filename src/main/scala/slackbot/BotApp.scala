package slackbot

import slackbot.core.Slackbot

/**
  * Created by tkassen on 5/26/17.
  */
object BotApp extends App {
  val apiToken: String = System.getenv("FRIENDPUTER_TOKEN")
  val bot: Slackbot = new Slackbot(apiToken)
}
