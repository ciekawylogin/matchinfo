package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawAction

case class Competition(id: String, name: String, matches: Seq[Match]) {
  def matchById(id: String): Option[Match] = matches.find(_.id == id)

  val numBestScorers = 10
  
  def bestScorers: Seq[Player] = {
    matches
      .flatMap(_.actions)
      .filter(_.action == "Goal")
      .groupBy(_.person)
      .collect { case (Some(playerName), goals) => Player(playerName, goals.head.team.getOrElse("?"), goals.size) }
      .toSeq
      .sortBy(- _.goals)
      .take(numBestScorers)
  }
}

object Competition {
  def fromRaw(competitionId: String, competitionName: String, matches: Seq[RawAction]): Competition = Competition(
    id = competitionId,
    name = competitionName,
    matches = matches.groupBy(_.matchId).toSeq.map {
      case (id, actions) =>
        Match.fromRaw(id = id, time = actions.head.date, actions = actions)
    }
  )
}

