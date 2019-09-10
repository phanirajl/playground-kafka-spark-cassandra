package org.playground.late.events.process.streaming

import org.apache.spark.sql.{ Dataset, SparkSession }
import org.apache.spark.sql.streaming.GroupStateTimeout

private[streaming] final case class GroupKey(timeWindow: TimeWindow, id: Long, name: String)

object CollectSequenceNumbers {
  private[streaming] def apply(
      windowedTimeEvent: Dataset[WindowedTimeEvent]
  )(implicit sparkSession: SparkSession): Dataset[Sequence] = {
    import sparkSession.implicits._

    windowedTimeEvent
      .groupByKey(x => x.createGroupKey)
      .mapGroupsWithState(GroupStateTimeout.EventTimeTimeout())(AggregateTimeEvents.aggregate)
  }
}
