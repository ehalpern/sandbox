package sandbox.app.core

import net.codingwell.scalaguice.ScalaModule

class ServiceModule extends ScalaModule {
  def configure {
    bind[FailService].to[FailServiceImpl]
  }
}
