package slackbot.core

import io.circe.Json
import slack.rtm.SlackRtmClient
import slackbot.handler.SlackbotTimedHandler

/**
  * Created by tkassen on 5/31/17.
  */
class HandlerTimer(h: SlackbotTimedHandler, r: SlackRtmClient, c: Map[String, String], u: Map[String, String]) {

  private val FALLBACK_TIMEOUT: Long = 60000;

  private val handler: SlackbotTimedHandler = h
  private var alive: Boolean = true
  private val rtmClient: SlackRtmClient = r
  private val channels: Map[String, String] = c
  private val users: Map[String, String] = u

  def getWrappingClassName(): String = {
    handler.getClass.getName
  }

  def getSaveData(): Json = {
    handler.onSaveRequest()
  }

  def start(): Boolean = {
    try {
      while (alive) {
        try {
          val response = handler.handleTimeout(channels, users)

          if (response._1)
            rtmClient.sendMessage(response._2, response._3)

        } catch {
          case Exception => println(s"There was an error processing the timeout for handler ${handler.getClass.getName}")
        }

        Thread.sleep(handler.getNextEventTimeout.orElse(FALLBACK_TIMEOUT))
      }
      true
    } catch {
      case e: Exception => {
        e.printStackTrace()
        false
      }
    }
  }

  def shutdown(): Unit = {
    alive = false
  }
}
