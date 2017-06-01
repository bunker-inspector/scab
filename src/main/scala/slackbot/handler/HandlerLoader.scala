package slackbot.handler

import org.clapper.classutil.{ClassFinder, ClassInfo}

import scala.collection.parallel.ParSeq
import scala.reflect.ClassTag

/**
  * Created by tkassen on 5/27/17.
  */
class HandlerLoader {
  val finder: ClassFinder = ClassFinder()
  val classes: Stream[ClassInfo] = finder.getClasses()
  val ClassMap: Map[String, ClassInfo] = ClassFinder.classInfoMap(classes)

  def loadHandlers[T]()(implicit tag: ClassTag[T]): ParSeq[T] = {
    val plugins: Iterator[ClassInfo] = ClassFinder.concreteSubclasses(tag.runtimeClass, ClassMap)

    plugins
      .map(clazz => {
        println(s"Found ${clazz.name}")
        Class.forName(clazz.name)
          .newInstance()
          .asInstanceOf[T]
    }).toSeq.par
  }
}
