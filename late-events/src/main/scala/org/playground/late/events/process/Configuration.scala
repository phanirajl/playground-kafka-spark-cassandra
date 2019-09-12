package org.playground.late.events.process

import com.typesafe.config.{ Config, ConfigFactory }

final case class Configuration(kafka: Kafka, spark: Spark) {
  def prettyPrint(): String =
    s"""
       |Configuration
       |  + Kafka
       |      - bootstrapServers .............. ${kafka.bootstrapServers}
       |      - time-event-topic .............. ${kafka.timeEventTopic}
       |  + Spark
       |      - application-name .............. ${spark.applicationName}
       |      - master ........................ ${spark.master}
       |      - compression-codec ............. ${spark.compressionCodec}
       |      - time-event-watermark .......... ${spark.timeEventWatermark}
       |      - tim-event-window-duration ..... ${spark.timeEventWindowDuration} 
       |""".stripMargin
}

final case class Kafka(bootstrapServers: String, timeEventTopic: String)

final case class Spark(compressionCodec: String,
                       master: String,
                       applicationName: String,
                       timeEventWatermark: String,
                       timeEventWindowDuration: String,
                       debugMode: String) {
  def getDebugMode: Boolean =
    if (debugMode.equalsIgnoreCase("on")) {
      true
    } else {
      false
    }
}

object Configuration {
  private val Filename = "application.conf"

  def load(): Configuration = {
    val configuration = ConfigFactory.load(Filename)
    from(configuration)
  }

  def from(config: Config): Configuration = {
    import pureconfig.generic.auto._ // Needed for pureconfig
    pureconfig.loadConfigOrThrow[Configuration](config)
  }
}
