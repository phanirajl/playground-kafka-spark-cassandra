package org.playground.late.events.process

import java.time.Duration
import java.util.Properties

import scala.collection.JavaConverters._
import scala.util.{ Failure, Success }

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.playground.late.events.generator.DataGenerator
import org.playground.late.events.protobuf.TimeEvent.TimeEvent

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
    properties.put(
      "key.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer"
    )
    properties.put(
      "value.deserializer",
      "org.apache.kafka.common.serialization.ByteArrayDeserializer"
    )
    properties.put("auto.offset.reset", "latest")
    properties.put("group.id", "consumer-group")
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

  def readFromKafka(configuration: Configuration): Unit = {
    val properties = createProperties(configuration)
    val consumer   = new KafkaConsumer[String, Array[Byte]](properties)
    consumer.subscribe(Seq(configuration.kafka.timeEventTopic).asJavaCollection)

    while (true) {
      val record = consumer.poll(Duration.ofSeconds(1L)).asScala
      for (data <- record.iterator) {
        TimeEvent.validate(data.value()) match {
          case Failure(exception) => println(s"Error: $exception")
          case Success(x) => println(s"key='${data.key()} value='$x")
        }
      }
    }
  }
}
