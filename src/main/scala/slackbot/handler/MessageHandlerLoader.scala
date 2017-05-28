package slackbot.handler

import org.clapper.classutil.{ClassFinder, ClassInfo, ClassUtil}

import scala.collection.parallel.{ParSeq, SeqSplitter}

/**
  * Created by tkassen on 5/27/17.
  */
class MessageHandlerLoader {
  def loadHandlers(): ParSeq[SlackBotMessageHandler] = {
    val finder: ClassFinder = ClassFinder()
    val classes: Stream[ClassInfo] = finder.getClasses()
    val ClassMap: Map[String, ClassInfo] = ClassFinder.classInfoMap(classes)
    val plugins: Iterator[ClassInfo] = ClassFinder.concreteSubclasses(classOf[SlackBotMessageHandler].getName, ClassMap)

    plugins.map(clazz => {
      println(s"Loading ${clazz.name}")

      Class.forName(clazz.name)
        .newInstance()
        .asInstanceOf[SlackBotMessageHandler]
    }).toSeq.par
  }
}
