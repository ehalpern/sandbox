package sandbox.app.api

import net.codingwell.scalaguice.{ScalaMultibinder, ScalaModule}
import sandbox.server.ApiPlugin

class ApiModule extends ScalaModule {
  def configure {
    val multi = ScalaMultibinder.newSetBinder[ApiPlugin](binder)
    multi.addBinding.to[FailApi]
    multi.addBinding.to[EchoApi]
  }
}
