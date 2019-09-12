package org.playground.late.events.process.streaming

import org.apache.spark.sql.streaming.GroupState

private[streaming] final case class Sequence(timeWindow: TimeWindow, id: Long, name: String, sequenceNumbers: Set[Int])

object AggregateTimeEvents {
  private[streaming] def aggregate(groupKey: GroupKey,
                                   incomingItems: Iterator[WindowedTimeEvent],
                                   oldState: GroupState[Sequence]) = {
    val state = getState(groupKey, oldState)

    incomingItems.foldLeft(state) { (accumulator, incomingItem) =>
      val oldSequenceNumbers = accumulator.sequenceNumbers
      val newSequenceNumbers = oldSequenceNumbers + incomingItem.sequenceNumber
      accumulator.copy(sequenceNumbers = newSequenceNumbers)
    }
  }

  private def getState(groupKey: GroupKey, oldState: GroupState[Sequence]) =
    if (oldState.exists) {
      oldState.get
    } else {
      Sequence(
        timeWindow      = groupKey.timeWindow,
        id              = groupKey.id,
        name            = groupKey.name,
        sequenceNumbers = Set.empty[Int]
      )
    }
}
