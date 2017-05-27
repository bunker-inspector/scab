package slackbot.handler;

import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.List;
import slack.models.Message;

class JavaDemoHandler implements SlackBotMessageHandler {
    @Override
    public Tuple3<Boolean, String, String> handleMessage(Message message, List<String> channels, List<Tuple2<String, String>> users) {
        return new Tuple3<>(false, message.channel(), "Java demo is live!");
    }
}
