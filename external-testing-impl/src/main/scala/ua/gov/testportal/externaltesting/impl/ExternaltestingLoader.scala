package ua.gov.testportal.externaltesting.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import ua.gov.testportal.externaltesting.api.ExternaltestingService
import com.softwaremill.macwire._

class ExternaltestingLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new ExternaltestingApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ExternaltestingApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[ExternaltestingService]
  )
}

abstract class ExternaltestingApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[ExternaltestingService].to(wire[ExternaltestingServiceImpl])
  )

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = ExternaltestingSerializerRegistry

  // Register the external-testing persistent entity
  persistentEntityRegistry.register(wire[ExternaltestingEntity])
}
