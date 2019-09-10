package org.playground.generator

import org.playground.protobufs.TimeEvent.TimeEvent

class TimeEventGenerator(var currentSequenceNumber: Int = 0, val id: Long) {
  def timeEvent(timestamp: Long): TimeEvent = {
    currentSequenceNumber = currentSequenceNumber + 1
    TimeEvent(
      id             = id,
      timestamp      = timestamp,
      name           = s"TimeEvent",
      description    = s"event number: $currentSequenceNumber",
      sequenceNumber = currentSequenceNumber
    )
  }
}
