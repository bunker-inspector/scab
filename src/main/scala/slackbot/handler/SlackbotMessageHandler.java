package slackbot.handler;

import io.circe.Json;
import io.circe.JsonObject;
import scala.Tuple3;
import scala.collection.immutable.Map;
import scala.util.parsing.json.JSONObject;
import slack.models.Message;


/**
 * Created by tkassen on 5/26/17.
 */
public interface SlackbotMessageHandler {

    default Tuple3<Boolean, String, String> noMessage() {
        return new Tuple3<>(false, null, null);
    }

    default void onInit(Json data) {

    }

    default Json onSaveRequest() {
        return Json.fromJsonObject(JsonObject.empty());
    }

    Tuple3<Boolean, String, String> handleMessage(Message message, Map<String, String> channels, Map<String, String> users);
}
