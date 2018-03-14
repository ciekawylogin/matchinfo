package top.krawczak.michal.matchinfo

import java.io.File

import com.github.tototoshi.csv._

import scala.io.Source

object MatchInfo {
  /**
    * Location of the dataset (in CSV) inside resources dir.
    *
    * @todo should be configurable (e.g. via typesafe config)
    */
  val datasetLocation = "dataset.csv"

  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(Source.fromResource(datasetLocation))
    println(reader.allWithHeaders())
  }
}
