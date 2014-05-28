package sandbox.frame.akka

import net.codingwell.scalaguice.ScalaModule
import akka.actor.{ActorRefFactory, ActorSystem}
import javax.inject.{Provider, Singleton}
import com.google.inject.{Provides, Injector}
import scala.concurrent.ExecutionContext

/**
 * Creates the root actor system and binds it to ActorSystem and ActorRefFactory.
 * Also registers GuiceAkkaExtension to simplify instantiating Guice injected
 * actors.
 */
class AkkaModule extends ScalaModule
{
  def configure {
    // providers are automatically bound
  }

  /**
   * Instantiates the root actor system and registers GuiceAkkaExtension.
   * This is annotated as @Singleton to ensure there is only one instance
   * created.
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

  @Provides @Singleton
  def provideExecutionContext(systemProvider: Provider[ActorSystem]): ExecutionContext = {
    systemProvider.get.dispatcher
  }
}
