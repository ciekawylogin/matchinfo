package top.krawczak.michal.matchinfo.domain

import java.util.Date

import top.krawczak.michal.matchinfo.domain.raw.RawAction

case class Match(id: String, time: Date, actions: Seq[Action]) {
  def hostTeam: Option[String] = actions find (_.isHome) flatMap (_.team)
  def guestTeam: Option[String] = actions find (_.isAway) flatMap (_.team)

  def hostLineUps: Seq[Action] = actions filter (x => x.isHome && x.isLineUp)
  def guestLineUps: Seq[Action] = actions filter (x => x.isAway && x.isLineUp)

  def hostSubstitutions: Seq[Action] = actions filter (x => x.isHome && x.isSubstitution)
  def guestSubstitutions: Seq[Action] = actions filter (x => x.isAway && x.isSubstitution)
}

object Match {
  def fromRaw(id: String, time: Date, actions: Seq[RawAction]): Match = Match(
    id = id,
    time = time,
    actions = actions map Action.fromRaw
  )
}