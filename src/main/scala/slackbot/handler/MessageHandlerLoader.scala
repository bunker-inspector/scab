package slackbot.handler

import org.reflections.Reflections

import scala.collection.parallel.ParSeq

/**
  * Created by tkassen on 5/27/17.
  */
class MessageHandlerLoader {
  def loadHandlers(): ParSeq[SlackBotMessageHandler] = {
    val handlerClasses = (new Reflections()).getSubTypesOf(classOf[SlackBotMessageHandler])
    val handlerCount = handlerClasses.size()

    return handlerClasses
      .toArray(new Array[Class[SlackBotMessageHandler]](handlerCount))
      .map(clazz => {
        println(s"Loading $clazz")
        clazz.newInstance()
      })
      .toSeq
      .par
  }
}
