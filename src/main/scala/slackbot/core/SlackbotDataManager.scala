package slackbot.core

import java.io.PrintWriter

import io.circe.{Json, JsonObject}
import io.circe.parser.decode
import io.circe.syntax._

import scala.io.Source

/**
  * Created by tkassen on 5/31/17.
  */
class SlackbotDataManager {
  private val DATAFILE_NAME: String = "resources/sbdb.json"

  var data: Map[String, Json] = decode[JsonObject](Source.fromFile("resources/sbdb.json").mkString)
    .getOrElse(JsonObject.empty)
    .toMap

  def getHandlerData(handlerName: String): Json  = {
    if (!data.contains(handlerName))
      data += (handlerName -> Json.obj())

    data.get(handlerName).get
  }

  def saveHandlerData(toSave: Map[String, Json]): Unit = {
    new PrintWriter(DATAFILE_NAME) { write(toSave.asJson.toString()); close }
  }
}
