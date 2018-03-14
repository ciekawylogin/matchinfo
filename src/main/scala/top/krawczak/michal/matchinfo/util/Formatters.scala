package top.krawczak.michal.matchinfo.util

object Formatters {
  def formatTime(time: Long): String = {
    val totalSecs = time / 1000
    val secs = totalSecs % 60
    val mins = totalSecs / 60
    s"$mins:$secs"
  }
}
