package top.krawczak.michal.matchinfo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akkahttptwirl.TwirlSupport
import com.github.tototoshi.csv._
import top.krawczak.michal.matchinfo.domain.{DataSet}
import top.krawczak.michal.matchinfo.domain.raw.RawAction
import top.krawczak.michal.matchinfo.server.Routes

import scala.concurrent.ExecutionContextExecutor
import scala.io.Source

object MatchInfo extends TwirlSupport with Routes {
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

  lazy val data: DataSet = {
    val reader = CSVReader open (Source fromResource datasetLocation)
    val rawData = reader.toStreamWithHeaders map RawAction.fromMap
    DataSet fromRaw rawData
  }

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val executor: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(routes, host, port)
  }
}
