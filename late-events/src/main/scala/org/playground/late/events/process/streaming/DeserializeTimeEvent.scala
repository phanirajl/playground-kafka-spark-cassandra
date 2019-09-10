package org.playground.late.events.process.streaming

import java.sql.Timestamp

import scala.util.{ Failure, Success }

import org.playground.late.events.protobuf.TimeEvent.{ TimeEvent => ProtoTimeEvent }
import org.slf4j.LoggerFactory

private[streaming] final case class TimeEvent(id: Long,
                                              timestamp: Timestamp,
                                              name: String,
                                              description: String,
                                              sequenceNumber: Int)

object DeserializeTimeEvent {
  private val Log = LoggerFactory.getLogger(this.getClass)

  private[streaming] def apply(serializedTimeEvent: Array[Byte]) =
    ProtoTimeEvent.validate(serializedTimeEvent) match {
      case Success(timeEvent) =>
        Some(
          TimeEvent(
            id             = timeEvent.id,
            timestamp      = new Timestamp(timeEvent.timestamp),
            name           = timeEvent.name,
            description    = timeEvent.description,
            sequenceNumber = timeEvent.sequenceNumber
          )
        )
      case Failure(exception) =>
        Log.error(
          "An error occurred during de-serializing a 'TimeEvent'. Exception was: ",
          exception
        )
        None
    }
}
