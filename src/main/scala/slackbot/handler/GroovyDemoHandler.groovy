package slackbot.handler

import scala.Tuple2
import scala.Tuple3
import scala.collection.immutable.List
import scala.collection.immutable.Map
import slack.models.Message
import slackbot.handler.SlackBotMessageHandler

class GroovyDemoHandler implements SlackBotMessageHandler {
    @Override
    Tuple3<Boolean, String, String> handleMessage(Message message, Map<String, String> channels, Map<String, String> users) {
        return new Tuple3<Boolean, String, String>(false, message.channel(), "Groovy handler is live!")
    }
}