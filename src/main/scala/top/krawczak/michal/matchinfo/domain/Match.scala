package top.krawczak.michal.matchinfo.domain

import java.util.Date

import top.krawczak.michal.matchinfo.domain.raw.RawMatchAction

case class Match(id: String, time: Date, actions: Seq[Action]) {
  def host: Option[String] = actions find (_.isHome) flatMap (_.team)
  def guest: Option[String] = actions find (_.isAway) flatMap (_.team)

  def hostLineUps: Seq[Action] = actions filter (_.isHome) filter (_.action == "Line-up")
  def guestLineUps: Seq[Action] = actions filter (_.isAway) filter (_.action == "Line-up")

  def hostSubstitutions: Seq[Action] = actions filter (_.isHome) filter (_.action == "Substitution")
  def guestSubstitutions: Seq[Action] = actions filter (_.isAway) filter (_.action == "Substitution")
}

object Match {
  def fromRaw(id: String, time: Date, actions: Seq[RawMatchAction]): Match = Match(
    id = id,
    time = time,
    actions = actions map Action.fromRaw
  )
}