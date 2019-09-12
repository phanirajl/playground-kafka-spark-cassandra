package org.playground.topic.reader

import com.typesafe.config.{ Config, ConfigFactory }

final case class Configuration(kafka: Kafka) {
  def prettyPrint(): String =
    s"""
       |Configuration
       |  + Kafka
       |      - bootstrapServers .............. ${kafka.bootstrapServers}
       |      - time-event-topic .............. ${kafka.timeEventTopic}
       |      - auto-offset-reset ............. ${kafka.autoOffsetReset}
       |      - group-id ...................... ${kafka.groupId}
       |""".stripMargin
}

final case class Kafka(bootstrapServers: String, timeEventTopic: String, autoOffsetReset: String, groupId: String)

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
