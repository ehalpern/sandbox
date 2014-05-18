package sandbox.core

import net.codingwell.scalaguice.{ScalaMultibinder, ScalaModule}

class ApiModule extends ScalaModule {
  def configure {
    ScalaMultibinder.newSetBinder[ApiRoute](binder).addBinding.to[ExampleApi]
  }
}
