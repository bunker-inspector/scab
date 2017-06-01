package slackbot.handler;

import scala.Tuple3;
import scala.collection.immutable.HashMap;
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

    default void onInit(JSONObject data) {

    }

    default JSONObject getData() {
        return new JSONObject(new HashMap<>());
    }

    Tuple3<Boolean, String, String> handleMessage(Message message, Map<String, String> channels, Map<String, String> users);
}
