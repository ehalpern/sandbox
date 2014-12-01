package twine.akka

import net.codingwell.scalaguice.ScalaModule
import akka.actor.{ActorRefFactory, ActorSystem}
import javax.inject.{Provider, Singleton}
import com.google.inject.{Provides, Injector}
import scala.concurrent.ExecutionContext

/**
 * This module defines the bindings required to support Guice injectable Akka actors.
 * It is a core module required to bootstrap the spray router for rest support.
 *
 * This includes:
 *
 * - ActorSystem      - a singleton instance of the root actor system
 * - ActorRefFactory  - the same instance bound as a ActorRefFactory.  (Guice will
 *                      only inject exact type matches, so we must bind the
 *                      actor system to ActorRefFactory even though ActorSystem
 *                      extends ActorRefFactory).
 * - ExecutionContext - a singleton instance of the execution context provided
 *                      by the root actor system.
 */
class AkkaModule extends ScalaModule
{
  def configure {
    // All of the bindings for this module are defined using the
    // [[https://github.com/google/guice/wiki/ProvidesMethods provider methods]]
    // below.
  }

  /**
   * Provides the singleton root-actor-system to be injected whenever an ActorSystem
   * is required.  This method also registers the GuiceAkkaExtension
   * to be used for instantiating guice injected actors.
   */
  @Provides @Singleton
  def provideActorSystem(injector: Injector) : ActorSystem = {
    val system = ActorSystem("root-actor-system")
    // initialize and register extension to allow akka to create actors using Guice
    GuiceAkkaExtension(system).initialize(injector)
    system
  }

  /**
   * Provides a singleton factory to be injected whenever an ActorRefFactory
   * is required.
   */
  @Provides @Singleton
  def provideActorRefFactory(systemProvider: Provider[ActorSystem]): ActorRefFactory = {
    systemProvider.get
  }

  /**
   * Provides a singleton execution context to be injected whenever an ExecutionContext
   * is required.
   */
  @Provides @Singleton
  def provideExecutionContext(systemProvider: Provider[ActorSystem]): ExecutionContext = {
    systemProvider.get.dispatcher
  }
}
