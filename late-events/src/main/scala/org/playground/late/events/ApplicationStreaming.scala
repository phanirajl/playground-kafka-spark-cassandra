package org.playground.late.events

import org.playground.late.events.process.Configuration
import org.playground.late.events.process.streaming.CreateStream
import org.slf4j.LoggerFactory

object ApplicationStreaming {
  private val Log = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    Log.info("Starting Spark Structured Streaming Application")
    val configuration = Configuration.load()
    Log.info(s"Configuration loaded: ${configuration.prettyPrint()}")
    CreateStream(configuration)
  }
}
