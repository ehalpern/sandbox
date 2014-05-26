package sandbox.main

import com.google.inject.Guice
import akka.actor.{ActorRefFactory, Props, ActorSystem}
import sandbox.util.guice.{GuiceAkkaExtension, ConfigBindingSupport, GuiceActorProducer}
import net.codingwell.scalaguice.ScalaModule
import sandbox.app._
import sandbox.util.config.ConfigFactory
import sandbox.server.{ApiRouterActor, Server}

object Main extends App
{
  // Create the actor system which will drive all execution
  implicit val actorSystem = ActorSystem("sandbox-system")

  // Create the root dependency injector
  val injector = Guice.createInjector(
    new ScalaModule with ConfigBindingSupport{
      def configure {
        bindConfig(ConfigFactory.load)
        bind[ActorRefFactory].toInstance(actorSystem)
      }
    },
    new Services,
    new Apis
  )

  GuiceAkkaExtension(actorSystem).initialize(injector)

  // Create the root akka service.  Provide a guice factory for akka to use
  // when instantiating the actor.  This is the key to integrating guice
  // injection with akka
  val routerActorRef = actorSystem.actorOf(
    GuiceAkkaExtension(actorSystem).props[ApiRouterActor]
  )

  injector.getInstance(classOf[Server]).start(routerActorRef)
}
