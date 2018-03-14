package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawMatchAction

case class Competition(id: String, name: String, matches: Seq[Match])

object Competition {
  def fromRaw(id: String, name: String, matches: Seq[RawMatchAction]): Competition = Competition(
    id = id,
    name = name,
    matches = matches.groupBy(_.matchId).toSeq.map {
      case (id, actions) =>
        Match.fromRaw(id = id, time = actions.head.date, actions = actions)
    }
  )
}

