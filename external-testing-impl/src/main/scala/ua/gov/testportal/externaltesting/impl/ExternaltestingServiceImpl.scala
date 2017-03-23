package ua.gov.testportal.externaltesting.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import ua.gov.testportal.externaltesting.api.ExternaltestingService

/**
  * Implementation of the ExternaltestingService.
  */
class ExternaltestingServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends ExternaltestingService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the external-testing entity for the given ID.
    val ref = persistentEntityRegistry.refFor[ExternaltestingEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the external-testing entity for the given ID.
    val ref = persistentEntityRegistry.refFor[ExternaltestingEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }
}
