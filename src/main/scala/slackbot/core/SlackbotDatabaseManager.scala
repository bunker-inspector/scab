package slackbot.core

import java.io.PrintWriter

import scala.io.Source
import scala.util.parsing.json.{JSON, JSONObject}

/**
  * Created by tkassen on 5/31/17.
  */
class SlackbotDatabaseManager {
  private val DB_FILENAME: String = "resources/sbdb.json"
  private var data: Map[String, Any] = JSON
      .parseFull(Source.fromFile(DB_FILENAME).mkString)
      .get
      .asInstanceOf[Map[String, Any]]


  def getHandlerData(handlerName: String): JSONObject = {
    new JSONObject(
      data
        .get(handlerName)
        .getOrElse(Map[String, Object]())
        .asInstanceOf[Map[String, Object]]
    )
  }

  def saveHandlerData(d: Map[String, JSONObject]): Unit = {
    new PrintWriter(DB_FILENAME) { write((new JSONObject(d)).toString()); close }
  }
}
