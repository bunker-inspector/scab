package slackbot.handler;

import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import slack.models.Message;

class JavaDemoHandler implements SlackBotMessageHandler {
    @Override
    public Tuple3<Boolean, String, String> handleMessage(Message message, Map<String, String> channels, Map<String, String> users) {
        return new Tuple3<>(false, message.channel(), "Java handler is live!");
    }
}
