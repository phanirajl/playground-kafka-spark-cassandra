package org.playground.late.events

import org.playground.late.events.protobuf.TimeEvent.TimeEvent

object Application {
  def main(args: Array[String]): Unit = {
    val timeEvent = TimeEvent(
      1L,
      System.currentTimeMillis(),
      "Single Time Event",
      1
    )
    println(s"the TimeEvent is '$timeEvent'.")
  }
}
