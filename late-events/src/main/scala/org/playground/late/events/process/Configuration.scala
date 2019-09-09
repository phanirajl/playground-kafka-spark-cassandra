package org.playground.late.events.process

import com.typesafe.config.{ Config, ConfigFactory }

final case class Configuration(kafka: Kafka, spark: Spark)

final case class Kafka(bootstrapServers: String, timeEventTopic: String)

final case class Spark(compressionCodec: String, applicationName: String)

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
