package slackbot.command

import java.lang

import slack.models.Message

/**
  * Created by tkassen on 5/27/17.
  */
class AppendHandler extends SlackBotMessageHandler {
  override def handleMessage(message: Message, channels: List[String],
                             users: List[(String, String)]): (lang.Boolean, String, String) = {
    return (true, message.channel, s"${message.text}APPENDED")
  }
}
