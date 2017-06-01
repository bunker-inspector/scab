package slackbot.core

import akka.actor.ActorSystem
import org.hamcrest.core.IsAnything
import slack.api.{BlockingSlackApiClient, SlackApiClient}
import slack.models.Message
import slack.rtm.{RtmState, SlackRtmClient}
import slackbot.handler.{HandlerLoader, SlackbotMessageHandler, SlackbotTimedHandler}

import scala.collection.parallel.ParSeq
import scala.concurrent.Future
import scala.util.Try

class Slackbot(val apiToken: String) {
  implicit val system = ActorSystem("slack")
  implicit val ec = system.dispatcher

  private val db: SlackbotDatabaseManager = new SlackbotDatabaseManager()
  private val blockingClient: BlockingSlackApiClient = BlockingSlackApiClient(apiToken)
  private val asyncClient: SlackApiClient = SlackApiClient(apiToken)
  private val channels: Map[String, String] = blockingClient.listChannels().map(channel => (channel.name, channel.id)).toMap[String, String]
  private val users: Map[String, String] = blockingClient.listUsers().map(user => (user.name, user.id)).toMap[String, String]
  private val rtmClient: SlackRtmClient = SlackRtmClient(apiToken)
  private val channelControllers: RtmState = rtmClient.state
  private var messageHandlerQueues: ParSeq[MessageQueue] = ParSeq()
  private var timedHandlers: ParSeq[HandlerTimer] = ParSeq()
  private val loader = new HandlerLoader()
  private var map: Map[String, Long] = Map()

  reloadHandlers()

  def reloadHandlers(): Unit = {
    stopHandlers()

    messageHandlerQueues = loader
      .loadHandlers[SlackbotMessageHandler]
      .map(handler => new MessageQueue(handler, rtmClient, channels, users))
      .par

    Future[Boolean](messageHandlerQueues.forall(_.start())).onComplete[Unit](runHandlers)

    timedHandlers = loader
      .loadHandlers[SlackbotTimedHandler]
      .map(handler => new HandlerTimer(handler, rtmClient, channels, users))
      .par

    Future[Boolean](timedHandlers.forall(_.start())).onComplete[Unit](runHandlers)
  }

  rtmClient.onMessage(message => {
    if (message.text.trim().length > 0) {
      if (message.text.trim() == "QUIT") {

        rtmClient.sendMessage(message.channel, "Shutting down...")
        println("Exiting!")
        close()
      }

      dispatchMessage(message)
    }
  })

  def runHandlers(result: Try[Boolean]): Unit = {
      val allExitedCorrectly: Boolean = result.getOrElse(false)
      if (!allExitedCorrectly) {
        throw new Exception("Not all handlers exited correctly")
      }
      else {
        println("All handlers exited correctly...")
      }
  }

  def dispatchMessage(message: Message): Unit = {
    messageHandlerQueues.foreach(handlerQueue => handlerQueue.enqueue(message))
  }

  private def stopHandlers(): Unit = {
    for (handleQueue <- messageHandlerQueues) { handleQueue.shutdown() }
    for (timer <- timedHandlers) { timer.shutdown() }
  }

  private def saveHandlerData(): Unit = {
    var toSave: Map[String, Map[String, Any]] = Map()

  }

  def close(): Unit = {
    println("Sending shutdown signal to handlers...")
    saveHandlerData()
    stopHandlers()
    println("Shutting down...")
    rtmClient.close()
  }
}
