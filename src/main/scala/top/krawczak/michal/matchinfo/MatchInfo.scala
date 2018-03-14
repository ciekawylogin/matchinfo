package top.krawczak.michal.matchinfo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akkahttptwirl.TwirlSupport
import top.krawczak.michal.matchinfo.input.CSVDataLoader
import top.krawczak.michal.matchinfo.server.Routes

import scala.concurrent.ExecutionContextExecutor

/**
  * Main class. Launches server at localhost:8080.
  *
  * @todo make the host and port configurable
  */
object MatchInfo extends TwirlSupport with CSVDataLoader with Routes {
  /**
    * Location of the dataset (in CSV) inside resources dir.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  protected val datasetLocation = "dataset.csv"

  /**
    * Port to listen to.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  private[this] val port = 8080

  /**
    * Service host.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  private[this] val host = "localhost"


  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val executor: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(routes(data), host, port)
  }
}
