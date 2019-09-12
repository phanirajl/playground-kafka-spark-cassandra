package org.playground.late.events.process.streaming

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.{ OutputMode, StreamingQuery }

object DebugOutput {
  private[streaming] def apply[T](dataset: Dataset[T]): StreamingQuery =
    dataset.writeStream
      .outputMode(OutputMode.Update())
      .option("truncate", false)
      .format("console")
      .start()
}
