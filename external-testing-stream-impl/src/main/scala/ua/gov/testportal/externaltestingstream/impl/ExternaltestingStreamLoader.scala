package ua.gov.testportal.externaltestingstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import ua.gov.testportal.externaltestingstream.api.ExternaltestingStreamService
import ua.gov.testportal.externaltesting.api.ExternaltestingService
import com.softwaremill.macwire._

class ExternaltestingStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new ExternaltestingStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ExternaltestingStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[ExternaltestingStreamService]
  )
}

abstract class ExternaltestingStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[ExternaltestingStreamService].to(wire[ExternaltestingStreamServiceImpl])
  )

  // Bind the ExternaltestingService client
  lazy val externaltestingService = serviceClient.implement[ExternaltestingService]
}
