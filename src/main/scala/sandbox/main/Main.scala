package sandbox.main

import com.google.inject.Guice
import sandbox.app._
import sandbox.frame.spray.{SprayModule, Server}
import sandbox.frame.akka.AkkaModule
import sandbox.frame.config.ConfigModule
import net.codingwell.scalaguice.ScalaModule

object Main extends App
{
  val injector = Guice.createInjector(new ScalaModule {
    override def configure() {
      installCore
      installServer
    }

    private def installCore() {
      install(new ConfigModule)
      install(new AkkaModule)
      install(new ServicesModule)
    }

    private def installServer() {
      install(new SprayModule)
      install(new ApisModule)
    }
  })

  injector.getInstance(classOf[Server]).start
}
