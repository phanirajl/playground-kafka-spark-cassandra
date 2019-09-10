package org.playground.generator

import scala.util.Random

import org.apache.kafka.clients.producer.ProducerRecord

class DataGenerator(configuration: Configuration) {
  private val timeEventGenerator = new TimeEventGenerator(0, 1234567890L)
  private val key                = new Random(10).nextString(10)
  private val topic              = configuration.kafka.timeEventTopic

  def generate(): ProducerRecord[String, Array[Byte]] = {
    val timestamp = System.currentTimeMillis()
    val value     = timeEventGenerator.timeEvent(timestamp)
    new ProducerRecord[String, Array[Byte]](topic, key, value.toByteArray)
  }
}
