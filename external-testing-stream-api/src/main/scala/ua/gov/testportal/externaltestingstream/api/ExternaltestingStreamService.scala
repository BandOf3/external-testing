package ua.gov.testportal.externaltestingstream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The external-testing stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the ExternaltestingStream service.
  */
trait ExternaltestingStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("external-testing-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

