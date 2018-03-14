package top.krawczak.michal.matchinfo.domain.raw

import java.text.SimpleDateFormat
import java.util.{Date, Locale}

import scala.util.Try

/**
  * Holds a single row read from the CSV, i.e. a single action.
  *
  * TODO Many of these fields are actually enum values (for example `action`, `period`, `function`).
  *      It would be better to represent them as enum-like types instead of string.
  */
case class RawAction(actionId: String,
                     competition: String,
                     matchId: String,
                     date: Date,
                     action: String,
                     period: Option[String] = None,
                     startTime: Option[Long] = None,
                     endTime: Option[Long] = None,
                     homeOrAway: Option[String] = None,
                     teamId: Option[String] = None,
                     team: Option[String] = None,
                     personId: Option[String] = None,
                     person: Option[String] = None,
                     function: Option[String] = None,
                     shirtNr: Option[String] = None,
                     actionReason: Option[String] = None,
                     actionInfo: Option[String] = None,
                     subpersonId: Option[String] = None,
                     subperson: Option[String] = None)

object RawAction {
  /**
    * Given a map where each property of an action is represented by a single entry with a known
    * key, convert it to a [[RawAction]].
    */
  def fromMap(map: Map[String, String]) = {
    def optionalString(key: String) = toStringOption(map.get(key))
    def optionalLong(key: String) = toNumberOption(map.get(key))
    RawAction(
      actionId = map("n_actionid"),
      competition = map("c_competition"),
      matchId = map("n_Matchid"),
      date = parseDateTime(map("d_date")),
      action = map("c_Action"),
      period = optionalString("c_Period"),
      startTime = optionalLong("n_StartTime"),
      endTime = optionalLong("n_Endtime"),
      homeOrAway = optionalString("c_HomeOrAway"),
      teamId = optionalString("n_TeamID"),
      team = optionalString("c_Team"),
      personId = optionalString("n_personid"),
      person = optionalString("c_person"),
      function = optionalString("c_Function"),
      shirtNr = optionalString("n_ShirtNr"),
      actionReason = optionalString("c_ActionReason"),
      actionInfo = optionalString("c_actionInfo"),
      subpersonId = optionalString("n_Subpersonid"),
      subperson = optionalString("c_Subperson")
    )
  }

  /**
    * Given a date in `dd-MMMM-yyyy HH:mm` format, convert it to Java [[Date]].
    */
  private[this] def parseDateTime(input: String): Date = {
    val formatter = new SimpleDateFormat("dd-MMMM-yyyy HH:mm", Locale.US)
    formatter.parse(input)
  }

  /**
    * Given a string which can be "NULL", convert it to [[Option]].
    */
  private[this] def toStringOption(input: Option[String]): Option[String] =
    if (input.isEmpty || input.contains("NULL"))
      None
    else
      input

  /**
    * Given a string which can be empty, "NULL" or a number, convert it to [[Option]]'al [[Long]].
    */
  private[this] def toNumberOption(input: Option[String]): Option[Long] =
    if (input.isEmpty || input.contains("NULL"))
      None
    else
      Try(input.get.trim.toLong).toOption
}