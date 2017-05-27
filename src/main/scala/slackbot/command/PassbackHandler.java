package slackbot.command;

import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.List;
import slack.models.Message;

class PassBackHandler implements SlackBotMessageHandler {
    @Override
    public Tuple3<Boolean, String, String> handleMessage(Message message, List<String> channels, List<Tuple2<String, String>> users) {
        return new Tuple3<>(true, message.channel(), message.text());
    }
}
