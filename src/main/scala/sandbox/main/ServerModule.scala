package sandbox.main

import net.codingwell.scalaguice.ScalaModule
import sandbox.util.guice.GuiceAkkaExtension
import sandbox.server.{Server, ApiRouterActor}
import com.google.inject.Provides
import akka.actor.{ActorRef, ActorSystem}
import javax.inject.{Named, Singleton}
import sandbox.app.ApisModule

/**
 * Adds HTTP server functionality and installs the ApisModule which registers
 * the REST APIs defined by the application.
 */
class ServerModule extends ScalaModule
{
  def configure {
    install(new ApisModule)
    bind[Server]
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
    system.actorOf(GuiceAkkaExtension(system).props[ApiRouterActor])
  }
}

