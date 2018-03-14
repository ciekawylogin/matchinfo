package top.krawczak.michal.matchinfo.domain

import java.util.Date

import top.krawczak.michal.matchinfo.domain.raw.RawMatchAction

case class Match(id: String, time: Date, actions: Seq[Action]) {
  def host: Option[String] = actions find (_.isHome) flatMap (_.team)
  def guest: Option[String] = actions find (_.isAway) flatMap (_.team)
}

object Match {
  def fromRaw(id: String, time: Date, actions: Seq[RawMatchAction]): Match = Match(
    id = id,
    time = time,
    actions = actions map Action.fromRaw
  )
}