package top.krawczak.michal.matchinfo.domain

import top.krawczak.michal.matchinfo.domain.raw.RawAction

case class DataSet(competitions: Seq[Competition]) {
  def competitionById(id: String): Option[Competition] = competitions.find(_.id == id)
}

object DataSet {
  def fromRaw(raw: Seq[RawAction]): DataSet =
    DataSet(raw.groupBy(_.competition).toSeq.zipWithIndex.map {
      case ((competitionName, matches), competitionId) =>
        Competition.fromRaw(competitionId.toString, competitionName, matches)
      }
    )
}