name := "match-info"

version := "0.1"

scalaVersion := "2.12.4"

val dependencies = Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.9",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "de.heikoseeberger" %% "akka-http-circe" % "1.19.0",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5"
)

libraryDependencies ++= dependencies

mainClass in Compile := Some("top.krawczak.michal.matchinfo.MatchInfo")
