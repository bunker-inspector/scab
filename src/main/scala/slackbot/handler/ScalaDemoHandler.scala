package slackbot.handler

import java.lang.Boolean

import slack.models.Message

/**
  * Created by tkassen on 5/27/17.
  */
class ScalaDemoHandler extends SlackBotMessageHandler {
  override def handleMessage(message: Message, channels: Map[String, String],
                             users: Map[String, String]): (Boolean, String, String) = {
    return (false, message.channel, "Scala handler is live!")
  }
}
