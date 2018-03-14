package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawMatchAction

case class Competition(id: String, name: String, matches: Seq[Match]) {
  def matchById(id: String): Option[Match] = matches.find(_.id == id)
}

object Competition {
  def fromRaw(competitionId: String, competitionName: String, matches: Seq[RawMatchAction]): Competition = Competition(
    id = competitionId,
    name = competitionName,
    matches = matches.groupBy(_.matchId).toSeq.map {
      case (id, actions) =>
        Match.fromRaw(id = id, time = actions.head.date, actions = actions)
    }
  )
}

