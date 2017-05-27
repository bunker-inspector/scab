package slackbot

import akka.actor.ActorSystem
import org.reflections.Reflections
import slack.api.{BlockingSlackApiClient, SlackApiClient}
import slack.models.{Message, User}
import slack.rtm.{RtmState, SlackRtmClient}
import slackbot.handler.{MessageHandlerLoader, SlackBotMessageHandler}
import scala.collection.parallel.ParSeq

class SlackBot(val apiToken: String) {
  implicit val system = ActorSystem("slack")
  implicit val ec = system.dispatcher

  private val blockingClient: BlockingSlackApiClient = BlockingSlackApiClient(apiToken)
  private val asyncClient: SlackApiClient = SlackApiClient(apiToken)
  private val channels: List[String] = blockingClient.listChannels().map(channel => channel.name).toList
  private val users: List[(String, String)] = blockingClient.listUsers().map(user => (user.name, user.id)).toList
  private val rtmClient: SlackRtmClient = SlackRtmClient(apiToken)
  private val channelControllers: RtmState = rtmClient.state
  private var handlers: ParSeq[SlackBotMessageHandler] = ParSeq()
  private val loader = new MessageHandlerLoader()

  reloadHandlers()

  def reloadHandlers(): Unit = {
    handlers = loader.loadHandlers()
  }

  rtmClient.onMessage(message => {
    if (message.text.trim() == "QUIT") {
      println("Exiting!")
      close()
    }

    dispatchMessage(message)
  })

  def dispatchMessage(message: Message): Unit = {
    handlers.foreach(handler => {
      val response = handler.handleMessage(message, channels, users)

      // if (1: Send response) then send (3: message text) to (2: User or channel)
      if (response._1) {
        rtmClient.sendMessage(response._2, response._3)
      }
    })
  }

  def close(): Unit = {
    rtmClient.close()
  }
}
