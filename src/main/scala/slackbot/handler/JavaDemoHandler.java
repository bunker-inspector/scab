package slackbot.handler;

import scala.Tuple3;
import scala.collection.immutable.Map;
import scala.util.parsing.json.JSONObject;
import slack.models.Message;

class JavaDemoHandler implements SlackbotMessageHandler {
    @Override
    public Tuple3<Boolean, String, String> handleMessage(Message message, Map<String, String> channels, Map<String, String> users) {
        return new Tuple3<>(false, message.channel(), "Java handler is live!");
    }
}
