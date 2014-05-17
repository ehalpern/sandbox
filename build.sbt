name := "sandbox"

version := "SNAPSHOT"

organization := "sandbox"

scalaVersion := "2.11.0"

// Add external conf directory to the classpath
unmanagedClasspath in Runtime += baseDirectory.value / "conf"

resolvers ++= Seq(
  "typesafe.com" at "http://repo.typesafe.com/typesafe/repo/",
  "sonatype.org" at "https://oss.sonatype.org/content/repositories/releases",
  "spray.io" at "http://repo.spray.io",
  "rediscala" at "http://dl.bintray.com/etaty/maven"
)

{
  libraryDependencies ++= Seq(
    "com.etaty.rediscala" %% "rediscala" % "1.3.1",
    "com.github.scopt" %% "scopt" % "3.2.0",
    "com.typesafe.akka" %% "akka-actor" % "2.3.2",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.2",
    "com.typesafe" % "config" % "1.2.0",
    "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
    "commons-io" % "commons-io" % "2.1",
    "io.spray" %% "spray-can" % "1.3.1-20140423",
    "io.spray" %% "spray-routing" % "1.3.1-20140423",
    "net.codingwell" %% "scala-guice" % "4.0.0-beta4",
    "org.apache.logging.log4j" % "log4j-api" % "2.0-rc1",
    "org.apache.logging.log4j" % "log4j-core" % "2.0-rc1",
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.0-rc1",
    "org.json4s" %% "json4s-jackson" % "3.2.9",
    "org.json4s" %% "json4s-ext" % "3.2.9",
    //          5
    // test dependencies
    //
    "io.spray" %% "spray-testkit" % "1.3.1-20140423" % "test",
    "org.scalatest" %% "scalatest" % "2.1.4" % "test",
    "org.scalamock" %% "scalamock-core" % "3.1.1" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.1.1" % "test"
  )
}