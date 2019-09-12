package org.playground.late.events.process.streaming

import org.apache.spark.sql.{ Dataset, SparkSession }
import org.playground.late.events.process.Configuration
import org.slf4j.LoggerFactory

object AddWatermark {
  private val Log                        = LoggerFactory.getLogger(this.getClass)
  private[streaming] val TimestampColumn = "timestamp"

  private[streaming] def apply(timeEvents: Dataset[TimeEvent],
                               configuration: Configuration)(implicit sparkSession: SparkSession) = {
    val watermark = configuration.spark.timeEventWatermark
    Log.info(s"Add watermark to 'TimeEvents' on column '$TimestampColumn' with watermark = '$watermark'.")

    timeEvents.withWatermark(TimestampColumn, watermark)
  }
}
