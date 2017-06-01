package slackbot.handler;

import scala.Tuple3;
import scala.collection.immutable.Map;
import scala.util.parsing.json.JSONObject;

import java.util.Optional;

/**
 * Created by tkassen on 5/31/17.
 */
public class ExampleTimerHandler implements SlackbotTimedHandler {
    @Override
    public Optional<Long> getNextEventTimeout() {
        return Optional.of(new Long(60000));
    }

    @Override
    public Tuple3<Boolean, String, String> handleTimeout(Map<String, String> channels, Map<String, String> users) {
        System.out.println("Friendputer is live...");
        return new Tuple3<>(false, channels.get("general").get(), "@here friendputer is live!");
    }
}
