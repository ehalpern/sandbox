package sandbox

import net.codingwell.scalaguice.ScalaModule
import sandbox.app._
import sandbox.frame.akka.AkkaModule
import sandbox.frame.config.ConfigModule
import sandbox.frame.spray.SprayModule

class MainModule extends ScalaModule
{
  override def configure() {
    installCore
    installServices
    installServer
  }

  private def installCore() {
    install(new ConfigModule)
    install(new AkkaModule)
  }

  private def installServices() {
    install(new ServicesModule)
  }

  private def installServer() {
    install(new SprayModule)
    install(new ApisModule)
  }
}
