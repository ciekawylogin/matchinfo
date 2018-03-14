package top.krawczak.michal.matchinfo.domain

sealed trait HomeOrAway

case object Home extends HomeOrAway
case object Away extends HomeOrAway

object HomeOrAway {
  def fromString(input: String): Map[String, HomeOrAway] =
    Seq(Home, Away).map(x => x.toString -> x).toMap
}