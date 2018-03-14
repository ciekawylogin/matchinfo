package top.krawczak.michal.matchinfo

import java.io.File

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.github.tototoshi.csv._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import top.krawczak.michal.matchinfo.domain.MatchAction

import scala.io.Source
import io.circe._, 
io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._


object MatchInfo extends ErrorAccumulatingCirceSupport{
  /**
    * Location of the dataset (in CSV) inside resources dir.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  val datasetLocation = "dataset.csv"

  /**
    * Port to listen to.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  val port = 8080

  /**
    * Service host.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  val host = "localhost"

  lazy val data = {
    val reader = CSVReader open (Source fromResource datasetLocation)
    reader.iteratorWithHeaders map MatchAction.fromList
  }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val executor = system.dispatcher
    implicit val materializer = ActorMaterializer()

    Http().bindAndHandle(routes, host, port)
  }


  val routes = {
    logRequestResult("akka-http-microservice") {
      get {
        path("match" / Segment) { segment =>
          complete(data.toArray.head)
        }
      }
    }
  }
}
