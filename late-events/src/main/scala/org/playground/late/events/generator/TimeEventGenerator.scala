package org.playground.late.events.generator

import org.playground.late.events.protobuf.TimeEvent.TimeEvent

class TimeEventGenerator(var currentSequenceNumber: Int = 0, val id: Long) {
  def timeEvent(timestamp: Long): TimeEvent = {
    currentSequenceNumber = currentSequenceNumber + 1
    TimeEvent(
      id             = id,
      timestamp      = timestamp,
      name           = s"TimeEvent-$currentSequenceNumber",
      sequenceNumber = currentSequenceNumber
    )
  }
}
