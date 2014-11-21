package twine

import com.google.inject.Guice
import twine.rest.RestServer

object Main extends App
{
  val injector = Guice.createInjector(new MainModule)

  injector.getInstance(classOf[RestServer]).start
}
