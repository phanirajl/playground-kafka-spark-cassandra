package org.playground.topic.reader

import org.slf4j.LoggerFactory

object Application {
  private val Log = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    Log.info("Starting Topic Reader Application")
    val configuration = Configuration.load()
    val properties    = CreateProperties(configuration)
    Log.info(s"Configuration loaded: ${configuration.prettyPrint()}")
    ReadTimeEvents(configuration, properties)
  }
}
