package top.krawczak.michal.matchinfo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.LoggingMagnet._
import akka.stream.ActorMaterializer
import akkahttptwirl.TwirlSupport
import com.github.tototoshi.csv._
import top.krawczak.michal.matchinfo.domain.{Competition, DataSet}
import top.krawczak.michal.matchinfo.domain.raw.RawMatchAction

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

  lazy val data: DataSet = {
    val reader = CSVReader open (Source fromResource datasetLocation)
    val rawData = reader.toStreamWithHeaders map RawMatchAction.fromList
    DataSet fromRaw rawData
  }

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val executor: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(routes, host, port)
  }

  import TwirlSupport.twirlHtmlMarshaller
  import akka.http.scaladsl.marshalling.PredefinedToResponseMarshallers.fromToEntityMarshaller
  val routes = {
    logRequestResult("matchinfo") {
      get {
        pathEndOrSingleSlash {
          complete {
            html.main render data
          }
        } ~
        path("competition" / Segment) { competitionId =>
          rejectEmptyResponse {
            complete {
              data competitionById competitionId map html.competition.render
            }
          }
        } ~
        path("competition" / Segment / "match" / Segment) { (competitionId, matchId) =>
          rejectEmptyResponse {
            complete {
              data competitionById competitionId flatMap (_ matchById matchId) map html.match_.render
            }
          }
        }
      }
    }
  }
}
