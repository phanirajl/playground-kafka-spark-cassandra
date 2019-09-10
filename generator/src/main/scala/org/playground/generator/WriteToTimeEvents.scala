package org.playground.generator

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer
import org.slf4j.LoggerFactory

object WriteToTimeEvents {
  private val Log = LoggerFactory.getLogger(this.getClass)

  def apply(configuration: Configuration, properties: Properties): Unit = {
    val generator = new DataGenerator(configuration)
    val producer  = new KafkaProducer[String, Array[Byte]](properties)
    while (true) {
      val record = generator.generate()
      Log.info(s"Sending to Kafka: $record")
      producer.send(record)

      Thread.sleep(1000L)
    }
    // haha
    producer.close()
  }
}
