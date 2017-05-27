import scala.Tuple2
import scala.Tuple3
import scala.collection.immutable.List
import slack.models.Message
import slackbot.handler.SlackBotMessageHandler

class GroovyDemoHandler implements SlackBotMessageHandler {
    @Override
    Tuple3<Boolean, String, String> handleMessage(Message message, List<String> channels, List<Tuple2<String, String>> users) {
        return new Tuple3<Boolean, String, String>(true, message.channel(), "Groovy handler is live!")
    }
}