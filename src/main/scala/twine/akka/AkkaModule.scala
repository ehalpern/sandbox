package twine.akka

import net.codingwell.scalaguice.ScalaModule
import akka.actor.{ActorRefFactory, ActorSystem}
import javax.inject.{Provider, Singleton}
import com.google.inject.{Provides, Injector}
import scala.concurrent.ExecutionContext

/**
 * This module defines the bindings required to support Guice injectable Akka actors.
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
   * Instantiates the root actor system and registers the GuiceAkkaExtension.
   * The @Singleton annotation ensures only one instance is created.
   */
  @Provides @Singleton
  def provideActorSystem(injector: Injector) : ActorSystem = {
    val system = ActorSystem("root-actor-system")
    // initialize and register extension to allow akka to create actors using Guice
    GuiceAkkaExtension(system).initialize(injector)
    system
  }

  /**
   * Binds ActorRefFactory to the root ActorSystem.  Note the the single binding
   * to ActorSystem is not enough because Guice will only inject exact type matches
   */
  @Provides @Singleton
  def provideActorRefFactory(systemProvider: Provider[ActorSystem]): ActorRefFactory = {
    systemProvider.get
  }

  /**
   * Binds ExecutionContext to the root ActorSystem ExecutionContext.
   */
  @Provides @Singleton
  def provideExecutionContext(systemProvider: Provider[ActorSystem]): ExecutionContext = {
    systemProvider.get.dispatcher
  }
}
