package org.playground.late.events

import org.playground.late.events.process.{ Configuration, KafkaService }

object ApplicationKafkaWriter {
  def main(args: Array[String]): Unit = {
    val configuration = Configuration.load()
    KafkaService.writeToKafka(configuration)
  }
}
