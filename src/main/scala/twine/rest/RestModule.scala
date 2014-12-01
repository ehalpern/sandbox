package twine.rest

import javax.inject.{Named, Singleton}

import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.Provides
import net.codingwell.scalaguice.ScalaModule
import twine.akka.GuiceAkkaExtension

/**
 * Adds HTTP server functionality and installs the ApisModule which registers
 * the REST APIs defined by the application.
 */
class RestModule extends ScalaModule
{
  def configure {
    bind[RestServer]
  }

  /**
   * Provides a named binding for the ActorRef that represents the ApiRouterActor.
   * The binding is named so that it will only be injected if the target explicitly
   * declares @Named("ApiRouterActorRef) actorRef: ActorRef.
   */
  @Provides
  @Singleton
  @Named("ApiRouterActorRef")
  def provideApiRouterActorRef(system: ActorSystem): ActorRef = {
    system.actorOf(GuiceAkkaExtension(system).props[RestServiceActor])
  }
}

