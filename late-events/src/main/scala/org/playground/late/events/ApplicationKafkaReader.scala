package org.playground.late.events

import org.playground.late.events.process.{ Configuration, KafkaService }

object ApplicationKafkaReader {
  def main(args: Array[String]): Unit = {
    val configuration = Configuration.load()
    KafkaService.readFromKafka(configuration)
  }
}
