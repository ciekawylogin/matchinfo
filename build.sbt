name := "match-info"

version := "0.1"

scalaVersion := "2.12.4"

resolvers ++= Seq(
  Resolver.bintrayRepo("btomala", "maven"),
  Resolver.bintrayRepo("hseeberger", "maven")
)

enablePlugins(SbtTwirl)

val dependencies = Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5",
  "btomala" %% "akka-http-twirl" % "1.2.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

libraryDependencies ++= dependencies

mainClass in Compile := Some("top.krawczak.michal.matchinfo.MatchInfo")
