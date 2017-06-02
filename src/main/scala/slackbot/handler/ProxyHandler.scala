package slackbot.handler
import java.lang

import slack.models.Message

//Me U2RK824GK
//Dan U2RL0MB7S
//Danny U39FF0QNR
//(kassen,U2RLCP4UX)

/**
  * Created by tkassen on 5/27/17.
  */
class ProxyHandler extends SlackbotMessageHandler {
  override def handleMessage(message: Message, channels: Map[String, String], users: Map[String, String]): (lang.Boolean, String, String) = {
    val tokens: Seq[String] = message.text.split(" ").map(_.trim)

    if(tokens(0).equals("proxy") && (tokens.size >= 4)) {
      try {
        val combinedMessage: String = tokens.slice(3, tokens.size).foldLeft(" ") { (a, b) =>s"$a $b"}
        if (tokens(1).toLowerCase().equals("chan")) {
          return (true, channels(tokens(2)), combinedMessage)
        }
      } catch {
        case e: Exception => {
          e.printStackTrace()
          return (true, message.channel, "There was an error in your command")
        }
      }
    }
    noMessage()
  }
}
