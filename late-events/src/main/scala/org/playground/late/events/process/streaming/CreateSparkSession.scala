package org.playground.late.events.process.streaming

import org.apache.spark.sql.SparkSession
import org.playground.late.events.process.Configuration
import org.slf4j.LoggerFactory

object CreateSparkSession {
  private val Log = LoggerFactory.getLogger(this.getClass)

  private[streaming] def apply(config: Configuration) = {
    val master           = config.spark.master
    val compressionCodec = config.spark.compressionCodec
    val applicationName  = config.spark.applicationName

    Log.info(
      s"Create SparkSession: master = '$master', compression-codec = '$compressionCodec', application-name = '$applicationName'."
    )
    SparkSession.builder
      .config("spark.io.compression.codec", compressionCodec)
      // .master(master)
      .master("local[2]")
      .appName(applicationName)
      .getOrCreate()
  }
}
