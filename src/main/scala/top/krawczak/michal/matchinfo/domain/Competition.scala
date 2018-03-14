package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawAction

case class Competition(id: String, name: String, matches: Seq[Match]) {
  /**
    * Find a match by its ID or return [[None]] if not found.
    */
  def matchById(id: String): Option[Match] = matches.find(_.id == id)

  val numBestScorers = 10

  /**
    * Fetch the list of [[numBestScorers]] best scorers, in descending order.
    */
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
  def fromRaw(competitionId: String, competitionName: String, rawActions: Seq[RawAction]): Competition = Competition(
    id = competitionId,
    name = competitionName,
    matches = rawActions.groupBy(_.matchId).toSeq.map {
      case (id, actions) =>
        Match.fromRaw(
          id = id,
          time = actions.head.date,
          actions = actions
        )
    }
  )
}

