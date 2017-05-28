package slackbot.handler

import java.lang.Boolean

import slack.models.Message

/**
  * Created by tkassen on 5/27/17.
  */
class RatingsBot extends SlackBotMessageHandler {
  private var ratings: Map[String, Int] = Map()

  override def handleMessage(message: Message, channels: Map[String, String],
                             users: Map[String, String]): (Boolean, String, String) = {
    val text: String = message.text.toLowerCase.trim

    if (text.split(" ").size == 1) {
      var currentScore: Int = 0
      var send: Boolean = false

      val token: String = text.substring(2)
      if (text.startsWith("++")) {
        currentScore = ratings.getOrElse(token, 0) + 1
        ratings += (token -> currentScore)
        send = true
      } else if (text.startsWith("--")) {
        currentScore = ratings.getOrElse(token, 0) - 1
        ratings += (token -> currentScore)
        send = true
      }
      return (send, message.channel.toString, s"$token has $currentScore points.")
    }
    return (false, "", "")
  }
}
