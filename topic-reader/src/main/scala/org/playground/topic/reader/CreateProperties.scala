package org.playground.topic.reader

import java.util.Properties

import org.slf4j.LoggerFactory

object CreateProperties {
  private val log = LoggerFactory.getLogger(this.getClass)

  def apply(configuration: Configuration): Properties = {
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
    properties.put(
      "auto.offset.reset",
      configuration.kafka.autoOffsetReset
    )
    properties.put("group.id", configuration.kafka.groupId)
    properties
  }
}
