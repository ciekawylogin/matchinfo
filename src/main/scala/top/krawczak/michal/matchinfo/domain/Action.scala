package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawAction

case class Action(period: Option[String],
                  action: String,
                  startTime: Option[Long],
                  endTime: Option[Long],
                  homeOrAway: Option[String],
                  team: Option[String],
                  person: Option[String],
                  function: Option[String],
                  shirtNr: Option[String],
                  actionReason: Option[String],
                  actionInfo: Option[String],
                  subperson: Option[String]) {
  def isHome: Boolean = homeOrAway contains "Home"
  def isAway: Boolean = homeOrAway contains "Away"

  def isSubstitution: Boolean = action == "Substitution"
  def isLineUp: Boolean = action == "Line-up"
}

object Action {
  def fromRaw(raw: RawAction) = Action(
    period = raw.period,
    action = raw.action,
    startTime = raw.startTime,
    endTime = raw.endTime,
    homeOrAway = raw.homeOrAway,
    team = raw.team,
    person = raw.person,
    function = raw.function,
    shirtNr = raw.shirtNr,
    actionReason = raw.actionReason,
    actionInfo = raw.actionInfo,
    subperson = raw.subperson
  )
}