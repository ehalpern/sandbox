package sandbox.main

import com.google.inject.Guice
import akka.actor.{Props, ActorSystem}
import sandbox.util.guice.{ConfigBindingSupport, GuiceInjectedActorProducer}
import net.codingwell.scalaguice.ScalaModule
import sandbox.api.ExampleApi
import sandbox.core.{ExampleServiceImpl, ExampleService}
import sandbox.util.config.ConfigFactory

object Main extends App
{
  // Create the actor system which will drive all execution
  implicit val actorSystem = ActorSystem("sandbox-system")

  // Create the root dependency injector
  val injector = Guice.createInjector(new ScalaModule with ConfigBindingSupport{
    def configure {
      bind[ExampleApi]
      bind[ExampleService].to[ExampleServiceImpl]
      bindConfig(ConfigFactory.load)
    }
  })

  // Create the root akka service.  Provide a guice factory for akka to use
  // when instantiating the actor.  This is the key to integrating guice
  // injection with akka
  val routerActorRef = actorSystem.actorOf(
    Props(classOf[GuiceInjectedActorProducer[ApiRouterActor]], injector, classOf[ApiRouterActor])
  )

  injector.getInstance(classOf[Server]).start(routerActorRef)
}
