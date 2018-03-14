package top.krawczak.michal.matchinfo.domain

import java.util.Date

case class Match(time: Date, actions: Seq[Action])