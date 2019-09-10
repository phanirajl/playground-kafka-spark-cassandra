package org.playground.topic.reader

import java.time.Duration
import java.util.Properties

import scala.collection.JavaConverters._
import scala.util.{ Failure, Success }

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.playground.protobufs.TimeEvent.TimeEvent
import org.slf4j.LoggerFactory

object ReadTimeEvents {
  private val Log = LoggerFactory.getLogger(this.getClass)

  def apply(configuration: Configuration, properties: Properties): Unit = {
    val consumer = new KafkaConsumer[String, Array[Byte]](properties)
    consumer.subscribe(Seq(configuration.kafka.timeEventTopic).asJavaCollection)

    while (true) {
      val record = consumer.poll(Duration.ofSeconds(1L)).asScala
      for (data <- record.iterator) {
        TimeEvent.validate(data.value()) match {
          case Failure(exception) => Log.error("An error occurred: Exception was: ", exception)
          case Success(x) =>
            Log.debug(
              s"Timestamp='${data.timestamp()}' Key='${data.key()} Value='$x' Offset='${data.offset()}' Partition='${data.partition()}'"
            )
        }
      }
    }
  }
}
