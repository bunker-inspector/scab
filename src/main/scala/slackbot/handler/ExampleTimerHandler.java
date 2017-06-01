package slackbot.handler;

import scala.Tuple3;
import scala.collection.immutable.Map;

import java.util.Optional;

/**
 * Created by tkassen on 5/31/17.
 */
public class ExampleTimerHandler implements SlackBotTimedHandler {
    @Override
    public Optional<Long> getNextEventTimeout() {
        return Optional.of(new Long(5000));
    }

    @Override
    public Tuple3<Boolean, String, String> handleTimeout(Map<String, String> channels, Map<String, String> users) {
        return new Tuple3<Boolean, String, String>(true, channels.get("general").get(), "This is a test timed message");
    }
}
