package top.krawczak.michal.matchinfo.server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.LoggingMagnet._
import akkahttptwirl.TwirlSupport
import top.krawczak.michal.matchinfo.domain.DataSet

trait Routes {
  import TwirlSupport.twirlHtmlMarshaller

  protected def routes(data: DataSet): Route = {
    logRequestResult("matchinfo") {
      get {
        // display all competitions
        pathEndOrSingleSlash {
          complete {
            html.main render data
          }
        } ~
        // display a list of matches in a single competition
        path("competition" / Segment) { competitionId =>
          rejectEmptyResponse {
            complete {
              data competitionById competitionId map html.competition.render
            }
          }
        } ~
      // display a single match info
        path("competition" / Segment / "match" / Segment) { (competitionId, matchId) =>
          rejectEmptyResponse {
            complete {
              data competitionById competitionId flatMap (_ matchById matchId) map html.mmatch.render
            }
          }
        }
      }
    }
  }
}
