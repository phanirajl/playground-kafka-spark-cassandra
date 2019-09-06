package org.playground.late.events.process

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer
import org.playground.late.events.generator.DataGenerator

object KafkaService {
  private def createProperties(configuration: Configuration) = {
    val properties = new Properties()
    properties.put(
      "bootstrap.servers",
      configuration.kafka.bootstrapServers
    )
    properties.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    properties.put(
      "value.serializer",
      "org.apache.kafka.common.serialization.ByteArraySerializer"
    )
    properties
  }

  def writeToKafka(configuration: Configuration): Unit = {
    val properties = createProperties(configuration)
    val generator  = new DataGenerator(configuration)
    val producer   = new KafkaProducer[String, Array[Byte]](properties)
    val record     = generator.generate()
    producer.send(record)
    producer.close()
  }
}
