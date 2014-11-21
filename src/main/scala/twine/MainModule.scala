package twine

import net.codingwell.scalaguice.ScalaModule
import twine.app._
import twine.akka.AkkaModule
import twine.config.ConfigModule
import twine.rest.RestModule

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
    install(new RestModule)
    install(new ApisModule)
  }
}
