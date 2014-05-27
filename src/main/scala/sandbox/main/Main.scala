package sandbox.main

import com.google.inject.Guice
import sandbox.app._
import sandbox.server.Server

object Main extends App
{
  val injector = Guice.createInjector(
    new ConfigModule,
    new AkkaModule,
    new ServicesModule,
    new ServerModule
  )

  injector.getInstance(classOf[Server]).start
}
