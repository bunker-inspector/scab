package slackbot.handler

import java.lang

import slack.models.Message

/**
  * Created by tkassen on 5/27/17.
  */
class ScalaDemoHandler extends SlackBotMessageHandler {
  override def handleMessage(message: Message, channels: List[String],
                             users: List[(String, String)]): (lang.Boolean, String, String) = {
    return (false, message.channel, "Scala handler is live!")
  }
}
