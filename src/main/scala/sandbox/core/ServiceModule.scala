package sandbox.core

import net.codingwell.scalaguice.ScalaModule

class ServiceModule extends ScalaModule {
  def configure {
    bind[ExampleService].to[ExampleServiceImpl]
  }
}
