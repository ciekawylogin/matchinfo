package top.krawczak.michal.matchinfo.domain

import java.text.SimpleDateFormat

import org.scalatest.{FlatSpec, Matchers}
import top.krawczak.michal.matchinfo.domain.raw.RawAction

class CompetitionSpec extends FlatSpec with Matchers {

  "Competition" should "pick the best scorers" in {
    val sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm")
    val competition = Competition.fromRaw("comp_name", "", Seq(
      new RawAction("0", "", "0", sdf.parse("11/08/2017 19:00"), "Goal", person = Some("person 1")),
      new RawAction("0", "", "1", sdf.parse("11/08/2017 19:00"), "Goal", person = Some("person 2")),
      new RawAction("0", "", "1", sdf.parse("11/08/2017 19:00"), "Goal", person = Some("person 1")),
      new RawAction("0", "", "1", sdf.parse("11/08/2017 19:00"), "Goal", person = Some("person 1")),
      new RawAction("0", "", "1", sdf.parse("11/08/2017 19:00"), "Foul commited", person = Some("person 1"))
    ))

    competition.bestScorers.size shouldBe 2
    competition.bestScorers.head.goals shouldBe 3
    competition.bestScorers.head.name shouldBe "person 1"
  }
}
