logLevel := sbt.Level.Debug

resolvers += "Sonatype Repository" at "https://oss.sonatype.org/content/groups/public"

addSbtPlugin("org.softnetwork.sbt.plugins" % "sbt-groovy" % "0.1.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0-M1")

enablePlugins(JavaAppPackaging)
