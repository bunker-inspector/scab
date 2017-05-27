package slackbot.command;

import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.List;
import slack.models.Message;


/**
 * Created by tkassen on 5/26/17.
 */
public interface SlackBotMessageHandler {
    Tuple3<Boolean, String, String> handleMessage(Message message, List<String> channels, List<Tuple2<String, String>> users);
}
