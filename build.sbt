name := "scab"

version := "1.0"

scalaVersion := "2.12.2"

Seq(groovy.settings :_*)
Seq(testGroovy.settings :_*)

libraryDependencies += "com.github.gilbertw1" %% "slack-scala-client" % "0.2.1"
libraryDependencies += "org.clapper" %% "classutil" % "1.1.2"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0"
