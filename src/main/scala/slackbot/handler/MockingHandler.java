package slackbot.handler;

import scala.Tuple3;
import scala.collection.immutable.Map;
import slack.models.Message;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by tkassen on 5/28/17.
 */
public class MockingHandler implements SlackBotMessageHandler {
    private static final String MOCKING_IMG_URL = "http://i2.kym-cdn.com/photos/images/facebook/001/255/479/85b.png";

    private static String toMocking(String notMocking) {
        char[] mocking = notMocking.toLowerCase().toCharArray();
        for (int i = 0; i < mocking.length; i+=2) {
            mocking[i] ^= 0x20;
        }
        return new String(mocking);
    }

    @Override
    public Tuple3<Boolean, String, String> handleMessage(Message message, Map<String, String> channels,
                                                         Map<String, String> users) {

        Random r = new Random();
        String text = message.text();

        if (r.nextInt(50) == 0) {
            String mockingMessage = Arrays.stream(text.split(" "))
                    .map(MockingHandler::toMocking)
                    .reduce(new String(),
                            (a, b) -> a + ' ' + b);
            return new Tuple3<>(true, message.channel(), mockingMessage + '\n' + MOCKING_IMG_URL);
        }
        return noMessage();
    }
}
