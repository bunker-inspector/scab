package slackbot.core

import slack.models.{Channel, Message}
import slack.rtm.SlackRtmClient
import slackbot.handler.SlackbotMessageHandler

import scala.collection.mutable
import scala.collection.mutable.Queue
import scala.util.parsing.json.JSONObject

/**
  * Created by tkassen on 5/27/17.
  */
class MessageQueue(h: SlackbotMessageHandler, r: SlackRtmClient, c: Map[String, String], u: Map[String, String]) {
  private final val DEQUE_TIMOUT_MS: Long = 100

  private val handler: SlackbotMessageHandler = h
  private val queue: Queue[Message] = new mutable.Queue[Message]()
  private var alive: Boolean = true
  private val rtmClient: SlackRtmClient = r
  private val channels: Map[String, String] = c
  private val users: Map[String, String] = u

  def getWrappingClassName(): String = {
    return handler.getClass.getName
  }

  def getData(): JSONObject = {
    return handler.getData()
  }

  def enqueue(message: Message): Unit = {
    queue.enqueue(message)
  }

  def start(): Boolean = {
    try {
      while (alive) {
        queue.dequeueFirst(message => {
          val response = handler.handleMessage(message, channels, users)

          if (response._1) {
            rtmClient.sendMessage(response._2, response._3)
          }

          true
        })
        Thread.sleep(DEQUE_TIMOUT_MS)
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
