package top.krawczak.michal.matchinfo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.LoggingMagnet._
import akka.stream.ActorMaterializer
import akkahttptwirl.TwirlSupport
import com.github.tototoshi.csv._
import top.krawczak.michal.matchinfo.domain.raw.MatchAction

import scala.concurrent.ExecutionContextExecutor
import scala.io.Source



object MatchInfo extends TwirlSupport {
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
    reader.toStreamWithHeaders map MatchAction.fromList
  }

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val executor: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(routes, host, port)
  }


  val routes = {
    import TwirlSupport.twirlHtmlMarshaller
    logRequestResult("matchinfo") {
      get {
        path("match" / Segment) { segment =>
          complete {
            html.twirl.render(data.head)
          }
        }
      }
    }
  }
}
