package slackbot

/**
  * Created by tkassen on 5/26/17.
  */
object BotApp extends App {
  val apiToken: String = System.getenv("FRIENDPUTER_TOKEN")
  val bot: SlackBot = new SlackBot(apiToken)
}
