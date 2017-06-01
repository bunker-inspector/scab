package slackbot.core

import slack.rtm.SlackRtmClient
import slackbot.handler.SlackBotTimedHandler

/**
  * Created by tkassen on 5/31/17.
  */
class HandlerTimer(h: SlackBotTimedHandler, r: SlackRtmClient, c: Map[String, String], u: Map[String, String]) {

  private val FALLBACK_TIMEOUT: Long = 5000;

  private val handler: SlackBotTimedHandler = h
  private var alive: Boolean = true
  private val rtmClient: SlackRtmClient = r
  private val channels: Map[String, String] = c
  private val users: Map[String, String] = u

  def start(): Boolean = {
    try {
      while (alive) {
        val response = handler.handleTimeout(channels, users)

        if (response._1) rtmClient.sendMessage(response._2, response._3)

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
