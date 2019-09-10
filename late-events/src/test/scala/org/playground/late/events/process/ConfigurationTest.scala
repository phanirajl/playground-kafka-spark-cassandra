package org.playground.late.events.process

import com.typesafe.config.ConfigFactory
import org.scalatest.{ Matchers, WordSpec }

class ConfigurationTest extends WordSpec with Matchers {
  "Configuration" should {
    "load a configuration" in {
      val subject = Configuration.from(ConfigFactory.load("test-application.conf").resolve())

      subject.kafka.bootstrapServers shouldBe "http://testserver/kafka:1234"
      subject.kafka.timeEventTopic shouldBe "test-time-event-topic"
      subject.spark.compressionCodec shouldBe "snappy"
      subject.spark.applicationName shouldBe "Playground Application"
      subject.spark.timeEventWatermark shouldBe "20 seconds"
      subject.spark.timeEventWindowDuration shouldBe "40 seconds"
    }
  }
}
