package top.krawczak.michal.matchinfo.input

import com.github.tototoshi.csv.CSVReader
import top.krawczak.michal.matchinfo.domain.DataSet
import top.krawczak.michal.matchinfo.domain.raw.RawAction

import scala.io.Source

trait CSVDataLoader {
  protected def datasetLocation: String

  protected lazy val data: DataSet = {
    val reader = CSVReader open (Source fromResource datasetLocation)
    val rawData = reader.toStreamWithHeaders map RawAction.fromMap
    DataSet fromRaw rawData
  }
}
