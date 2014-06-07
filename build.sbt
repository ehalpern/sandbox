name := "sandbox"

version := "SNAPSHOT"

organization := "sandbox"

scalaVersion := "2.11.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

// Add external conf directory to the classpath
unmanagedClasspath in Test += baseDirectory.value / "conf"

unmanagedClasspath in IntegTest += baseDirectory.value / "conf"

unmanagedClasspath in Runtime += baseDirectory.value / "conf"

resolvers ++= Seq(
  "typesafe.com" at "http://repo.typesafe.com/typesafe/repo/",
  "sonatype.org" at "https://oss.sonatype.org/content/repositories/releases",
  "spray.io" at "http://repo.spray.io",
  "rediscala" at "http://dl.bintray.com/etaty/maven"
)

{
  val AkkaVersion = "2.3.2"
  val Json4sVersion = "3.2.9"
  val Log4jVersion = "2.0-rc1"
  val SprayVersion = "1.3.1-20140423"
  val ScalaTestVersion = "2.1.4"
  libraryDependencies ++= Seq(
    "com.github.scopt" %% "scopt" % "3.2.0",
    "com.google.guava" % "guava" % "17.0",
    "com.google.inject" % "guice" % "4.0-beta4",
    "com.google.inject.extensions" % "guice-multibindings" % "4.0-beta4",
    "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
    "com.typesafe" % "config" % "1.2.0",
    "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
    "commons-io" % "commons-io" % "2.1",
    "io.spray" %% "spray-can" % SprayVersion,
    "io.spray" %% "spray-client" % SprayVersion,
    "io.spray" %% "spray-json" % "1.2.6",
    "io.spray" %% "spray-routing" % SprayVersion,
    "org.apache.logging.log4j" % "log4j-api" % Log4jVersion,
    "org.apache.logging.log4j" % "log4j-core" % Log4jVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % Log4jVersion,
    "org.json4s" %% "json4s-jackson" % Json4sVersion,
    "org.json4s" %% "json4s-ext" % Json4sVersion,
    //-------------------------------------------------------------------------
    "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % "test",
    "io.spray" %% "spray-testkit" % SprayVersion % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.scalatest" %% "scalatest" % ScalaTestVersion % "test"
  )
}