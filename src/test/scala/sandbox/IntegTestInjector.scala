package sandbox

import com.google.inject.Provides
import sandbox.app._
import sandbox.frame.spray.{ApiRouter, ApiPlugin, SprayModule}
import sandbox.frame.akka.AkkaModule
import sandbox.frame.config.ConfigModule
import net.codingwell.scalaguice.ScalaModule
import akka.actor.ActorRefFactory

trait IntegTestInjector extends TestInjector
{
  final override protected[this] def bindings = new ScalaModule {
    def configure() = {
      install(new ConfigModule)
      install(new AkkaModule)
      install(new ServicesModule)
      install(new SprayModule)
      install(new ApisModule)
    }

    @Provides
    def provideApiRouter(apiSet: Set[ApiPlugin], arf: ActorRefFactory): ApiRouter = {
      new ApiRouter {
        implicit def actorRefFactory = arf
        protected[this] def apis = apiSet.toSeq
      }
    }
  }
}


