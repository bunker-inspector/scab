package slackbot.handler;

import scala.Tuple3;
import scala.collection.immutable.Map;

import java.util.Optional;

/**
 * Created by tkassen on 5/31/17.
 */
public interface SlackBotTimedHandler {
    Optional<Long> getNextEventTimeout();
    Tuple3<Boolean, String, String> handleTimeout(Map<String, String> channels, Map<String, String> users);
}
