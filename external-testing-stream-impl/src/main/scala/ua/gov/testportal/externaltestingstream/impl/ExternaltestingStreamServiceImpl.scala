package ua.gov.testportal.externaltestingstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import ua.gov.testportal.externaltestingstream.api.ExternaltestingStreamService
import ua.gov.testportal.externaltesting.api.ExternaltestingService

import scala.concurrent.Future

/**
  * Implementation of the ExternaltestingStreamService.
  */
class ExternaltestingStreamServiceImpl(externaltestingService: ExternaltestingService) extends ExternaltestingStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(externaltestingService.hello(_).invoke()))
  }
}
