package slackbot.handler

import scala.Tuple3
import scala.collection.immutable.Map
import slack.models.Message

/**
 * Created by tkassen on 5/27/17.
 */
class KotlinDemoHandler : SlackBotMessageHandler {
    override fun handleMessage(message: Message?, channels: Map<String, String>?, users: Map<String, String>?): Tuple3<Boolean, String, String> {
            return Tuple3(false, message!!.channel(), "Kotlin handler is live!")
    }
}