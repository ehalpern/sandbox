package sandbox.util.guice

import akka.actor.{Actor, IndirectActorProducer}
import com.google.inject.Injector

/**
 * Allows akka to instantiate guice injected instances
 */
class GuiceInjectedActorProducer[A <: Actor](injector: Injector, actorClz: Class[A])
  extends IndirectActorProducer
{
  override def actorClass = actorClz
  override def produce = injector.getInstance(actorClass)
}



//val actorRef = system.actorOf(Props(classOf[DependencyInjector], applicationContext, "hello"), "helloBean")
