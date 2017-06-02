package slackbot.handler;

import io.circe.Json;
import io.circe.JsonObject;
import scala.Tuple3;
import scala.collection.immutable.Map;

import java.util.Optional;

/**
 * Created by tkassen on 5/31/17.
 */
public interface SlackbotTimedHandler {
    default void onInit(Json data) {

    }

    default Json onSaveRequest() {
        return Json.fromJsonObject(JsonObject.empty());
    }

    Optional<Long> getNextEventTimeout();
    Tuple3<Boolean, String, String> handleTimeout(Map<String, String> channels, Map<String, String> users);
}
