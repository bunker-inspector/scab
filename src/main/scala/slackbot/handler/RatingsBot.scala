package slackbot.handler

import java.lang.Boolean

import io.circe.{Json, JsonNumber, JsonObject}
import slack.models.Message

/**
  * Created by tkassen on 5/27/17.
  */
class RatingsBot extends SlackbotMessageHandler {
  private var ratings: Map[String, Json] = null

  override def onInit(data: Json): Unit = {
    ratings = data.asObject.getOrElse(JsonObject.empty).toMap
  }

  override def onSaveRequest(): Json = {
    Json.fromJsonObject(JsonObject.fromMap(ratings))
  }

  override def handleMessage(message: Message, channels: Map[String, String],
                             users: Map[String, String]): (Boolean, String, String) = {
    val text: String = message.text.toLowerCase.trim

    if (text.equals("!scoreboard")) {
      val scores: String = ratings
        .foldLeft[List[String]](List[String]()) { (acc, elem) =>  s"${elem._1} : ${elem._2.asNumber.getOrElse(0)}" :: acc }
        .mkString("\n")

      return (scores.length > 0, message.channel, scores)
    }
    else if (text.split(" ").size == 1) {
      var currentScore: Int = 0
      var send: Boolean = false

      val token: String = text.substring(2)
      if (text.startsWith("++")) {
        currentScore = ratings.getOrElse(token, Json.fromInt(0)).as[Int].getOrElse(0) + 1
        ratings += (token -> Json.fromInt(currentScore))
        send = true
      } else if (text.startsWith("--")) {
        currentScore = ratings.getOrElse(token, Json.fromInt(0)).as[Int].getOrElse(0) - 1
        ratings += (token -> Json.fromInt(currentScore))
        send = true
      }
      return (send, message.channel.toString, s"$token has $currentScore points.")
    }

    return noMessage()
  }
}
