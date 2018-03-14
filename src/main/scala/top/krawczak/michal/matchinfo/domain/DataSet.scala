package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawMatchAction

case class DataSet(competitions: Seq[Competition]) {
  def competitionById(id: String): Option[Competition] = competitions.find(_.id == id)
}

object DataSet {
  def fromRaw(raw: Seq[RawMatchAction]): DataSet =
    DataSet(raw.groupBy(_.competition).toSeq.zipWithIndex.map {
      case ((competitionName, matches), competitionId) =>
        Competition.fromRaw(competitionId.toString, competitionName, matches)
      }
    )
}