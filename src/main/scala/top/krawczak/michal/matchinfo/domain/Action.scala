package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawAction

/**
  * Holds a single match action..
  *
  * TODO Many of these fields are actually enum values (for example `action`, `period`, `function`).
  *      It would be better to represent them as enum-like types instead of strings.
  */
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
  /**
    * Was it performed by the home team?
    */
  def isHome: Boolean = homeOrAway contains "Home"
  /**
    * Was it performed by the away team?
    */
  def isAway: Boolean = homeOrAway contains "Away"

  /**
    * Is it a substitution?
    */
  def isSubstitution: Boolean = action == "Substitution"
  /**
    * Is it a line-up?
    */
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