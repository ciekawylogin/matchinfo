package top.krawczak.michal.matchinfo.util

object Formatters {
  /**
    * Format a time (given as number of miliseconds from the start of the match) as "minutes:seconds".
    */
  def formatTime(time: Long): String = {
    val totalSecs = time / 1000
    val secs = totalSecs % 60
    val mins = totalSecs / 60
    f"$mins:$secs%02d"
  }
}
