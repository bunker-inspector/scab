package slackbot.core

import akka.actor.ActorSystem
import akka.actor.Status.Success
import slack.api.{BlockingSlackApiClient, SlackApiClient}
import slack.models.Message
import slack.rtm.{RtmState, SlackRtmClient}
import slackbot.handler.{MessageHandlerLoader, SlackBotMessageHandler}

import scala.collection.parallel.ParSeq
import scala.concurrent.Future

class SlackBot(val apiToken: String) {
  implicit val system = ActorSystem("slack")
  implicit val ec = system.dispatcher

  private val blockingClient: BlockingSlackApiClient = BlockingSlackApiClient(apiToken)
  private val asyncClient: SlackApiClient = SlackApiClient(apiToken)
  private val channels: Map[String, String] = blockingClient.listChannels().map(channel => (channel.name, channel.id)).toMap[String, String]
  private val users: Map[String, String] = blockingClient.listUsers().map(user => (user.name, user.id)).toMap[String, String]
  private val rtmClient: SlackRtmClient = SlackRtmClient(apiToken)
  private val channelControllers: RtmState = rtmClient.state
  private var handlerQueues: ParSeq[MessageQueue] = ParSeq()
  private val loader = new MessageHandlerLoader()

  reloadHandlers()

  def reloadHandlers(): Unit = {
    stopHandlers()

    handlerQueues = loader
      .loadHandlers()
      .map(handler => new MessageQueue(handler, rtmClient, channels, users))
      .par

    Future[Boolean](handlerQueues.forall(_.start())).onComplete[Unit](result => {
      val allExitedCorrectly: Boolean = result.getOrElse(false)
      if (!allExitedCorrectly) {
        throw new Exception("Not all handlers exited correctly")
      }
      else {
        println("All handlers exited correctly...")
      }
    })
  }


  rtmClient.onMessage(message => {
    if (message.text.trim() == "QUIT") {
      println("Exiting!")
      close()
    }

    dispatchMessage(message)
  })

  def dispatchMessage(message: Message): Unit = {
    handlerQueues.foreach(handlerQueue => handlerQueue.enqueue(message))
  }

  private def stopHandlers(): Unit = {
    for (handleQueue <- handlerQueues) { handleQueue.shutdown() }
  }

  def close(): Unit = {
    println("Sending shutdown signal to handlers...")
    stopHandlers()
    println("Shutting down...")
    rtmClient.close()
  }
}
