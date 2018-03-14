package top.krawczak.michal.matchinfo.server

import akka.http.scaladsl.model.Uri.Path.Segment
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.LoggingMagnet._
import akkahttptwirl.TwirlSupport
import top.krawczak.michal.matchinfo.MatchInfo.data

trait Routes {
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
