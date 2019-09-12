package org.playground.late.events.process.streaming

import org.apache.spark.sql.{ Dataset, SparkSession }
import org.playground.late.events.process.Configuration
import org.slf4j.LoggerFactory

object CreateStream {
  private val Log = LoggerFactory.getLogger(this.getClass)

  def apply(configuration: Configuration): Unit = {
    Log.info("Create Stream for 'TimeEvents'.")
    implicit val sparkSession: SparkSession = CreateSparkSession(configuration)
    import sparkSession.implicits._

    val serializedTimeEvents: Dataset[Array[Byte]]     = ReadFromTimeEventsTopic(configuration)
    val timeEvents: Dataset[TimeEvent]                 = serializedTimeEvents.flatMap(x => DeserializeTimeEvent(x))
    val watermarkedTimeEvents: Dataset[TimeEvent]      = AddWatermark(timeEvents, configuration)
    val windowedTimeEvents: Dataset[WindowedTimeEvent] = AddTimeWindow(watermarkedTimeEvents, configuration)
    val collectedSequences                             = CollectSequenceNumbers(windowedTimeEvents)

    // Debugging
    val debugItems: Seq[Dataset[_]] = Seq(timeEvents, windowedTimeEvents, collectedSequences)
    debug(debugItems, configuration)

    sparkSession.streams.awaitAnyTermination()
  }

  private def debug(dataSets: Seq[Dataset[_]], configuration: Configuration) = if (configuration.spark.getDebugMode) {
    dataSets.map(item => DebugOutput(item))
  }
}
