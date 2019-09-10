package org.playground.late.events.process.streaming

import org.apache.spark.sql.{ DataFrame, SparkSession }
import org.playground.late.events.process.Configuration
import org.slf4j.LoggerFactory

object ReadFromTimeEventsTopic {
  private val Log = LoggerFactory.getLogger(this.getClass)

  private[streaming] def apply(config: Configuration)(implicit sparkSession: SparkSession) = {
    val bootstrapServers = config.kafka.bootstrapServers
    val topic            = config.kafka.timeEventTopic

    Log.info(s"Create StreamingSource (Kafka): bootstrapServers = '$bootstrapServers', topic = '$topic'.")
    import sparkSession.implicits._

    sparkSession.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topic)
      .load()
      .map(row => row.getAs[Array[Byte]]("value"))
  }
}
