package org.playground.late.events.process.streaming

import java.sql.Timestamp

import org.apache.spark.sql.{ Dataset, SparkSession }
import org.playground.late.events.process.Configuration
import org.playground.late.events.process.streaming.AddWatermark.TimestampColumn
import org.slf4j.LoggerFactory

private[streaming] final case class TimeWindow(start: Timestamp, end: Timestamp)

private[streaming] final case class WindowedTimeEvent(id: Long,
                                                      timestamp: Timestamp,
                                                      name: String,
                                                      description: String,
                                                      sequenceNumber: Int,
                                                      timeWindow: TimeWindow) {
  private[streaming] def createGroupKey = GroupKey(timeWindow = timeWindow, id = id, name = name)
}

object AddTimeWindow {
  private val Log              = LoggerFactory.getLogger(this.getClass)
  private val TimeWindowColumn = "timeWindow"

  private[streaming] def apply(timeEvents: Dataset[TimeEvent],
                               configuration: Configuration)(implicit sparkSession: SparkSession) = {
    val duration = configuration.spark.timeEventWindowDuration
    Log.info(s"Add time-window to 'TimeEvent' for grouping, duration = '$duration'.")

    import org.apache.spark.sql.functions._
    import sparkSession.implicits._

    timeEvents
      .withColumn(
        TimeWindowColumn,
        window(col(TimestampColumn), duration)
      )
      .as[WindowedTimeEvent]
  }
}
