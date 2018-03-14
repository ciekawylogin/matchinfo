package top.krawczak.michal.matchinfo.util

import org.scalatest.{FlatSpec, Matchers}

class FormattersSpec extends FlatSpec with Matchers {

  "Formatters" should "convert match time from miliseconds to mm:ss" in {
    Formatters.formatTime(558000) shouldBe "9:18"
  }

  it should "use 2-digit format for seconds, always" in {
    Formatters.formatTime(0) shouldBe "0:00"
    Formatters.formatTime(4327000) shouldBe "72:07"
  }
}
