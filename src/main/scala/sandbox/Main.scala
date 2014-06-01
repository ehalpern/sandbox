package sandbox

import com.google.inject.Guice
import sandbox.frame.spray.Server

object Main extends App
{
  val injector = Guice.createInjector(new MainModule)

  injector.getInstance(classOf[Server]).start
}
