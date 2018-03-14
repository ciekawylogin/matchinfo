package top.krawczak.michal.matchinfo.domain

import com.github.tototoshi.csv.CSVReader
import org.scalatest._
import top.krawczak.michal.matchinfo.domain.raw.RawAction

import scala.io.Source

class RawActionInputSpec extends FlatSpec with Matchers {

  "Raw actions" should "be read from CSV" in {
    val rows = parseCSV(exampleCsvContents)

    rows.size shouldBe 3
    rows.head.action shouldBe "Foul committed"
    rows.last.person shouldBe Some("Rens Bluemink")
  }

  it should "be converted to domain data" in {
    val rows = parseCSV(exampleCsvContents)
    val domain = DataSet fromRaw rows

    domain.competitions.size shouldBe 2
    val comp1718 = domain.competitions.maxBy(_.matches.size)
    comp1718.matches.size shouldBe 2
    comp1718.matches.exists(_.hostTeam.contains("ADO Den Haag")) shouldBe true
    comp1718.matches.exists(_.hostTeam.contains("Legia Warszawa")) shouldBe false
  }

  private def parseCSV(csv: String) = {
    val reader = CSVReader open (Source fromString csv)
    reader.toStreamWithHeaders map RawAction.fromMap
  }

  val exampleCsvContents: String =
    """
      |n_actionid,c_competition,n_Matchid,d_date,c_Action,c_Period,n_StartTime,n_Endtime,c_HomeOrAway,n_TeamID,c_Team,n_personid,c_person,c_Function,n_ShirtNr,c_ActionReason,c_actionInfo,n_Subpersonid,c_Subperson
      |22039489,Eredivisie 2017/2018,2174508,11-Aug-2017 19:00,Foul committed,First half,24000,24000,Home,77,ADO Den Haag,1223164,Sheraldo Becker,NULL,NULL,Rough play,NULL,924566,Anouar Kali
      |22039490,Eredivisie 2017/2018,2174501,15-Aug-2017 17:30,Free kick,First half,29000,29000,Away,16,FC Utrecht,924566,Anouar Kali,NULL,NULL,NULL,Left foot,0,NULL
      |22024166,Eredivisie 2016/2017,2174500,11-Sep-2016 16:00,Line-up,Start,NULL,NULL,,0,NULL,917392,Rens Bluemink,Assistant Referee,NULL,NULL,NULL,0,NULL
    """.trim.stripMargin
}