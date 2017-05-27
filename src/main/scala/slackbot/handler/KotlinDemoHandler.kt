package slackbot.handler

import scala.Tuple2
import scala.Tuple3
import scala.collection.immutable.List
import slack.models.Message

/**
 * Created by tkassen on 5/27/17.
 */
class KotlinDemoHandler : SlackBotMessageHandler {
    override fun handleMessage(message: Message?, channels: List<String>?, users: List<Tuple2<String, String>>?): Tuple3<Boolean, String, String> {
        return Tuple3(true, message!!.channel(), "Kotlin handler is online!")
    }
}