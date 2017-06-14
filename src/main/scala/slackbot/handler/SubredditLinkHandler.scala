package slackbot.handler
import java.lang

import slack.models.Message

/**
  * Created by tkassen on 6/14/17.
  */
class SubredditLinkHandler extends SlackbotMessageHandler {
  override def handleMessage(message: Message, channels: Map[String, String], users: Map[String, String]): (lang.Boolean, String, String) = {
    val result: String = message
      .text
      .split(" ")
      .filter(_.startsWith("/r/"))
      .foldLeft[List[String]](List[String]()) { (acc, elem) =>  s"http://reddit.com$elem" :: acc }
      .mkString("\n")

    (result.length > 0, message.channel, result)
  }
}
