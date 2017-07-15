name := "scab"

version := "1.0"

scalaVersion := "2.12.2"

Seq(groovy.settings :_*)
Seq(testGroovy.settings :_*)

libraryDependencies += "com.github.gilbertw1" %% "slack-scala-client" % "0.2.1"
libraryDependencies += "org.clapper" %% "classutil" % "1.1.2"

val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

enablePlugins(JavaAppPackaging)