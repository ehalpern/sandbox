package sandbox

import com.google.inject.{Module, Guice}
import net.codingwell.scalaguice.ScalaModule

trait TestInjector {
  protected val injector = Guice.createInjector(new ScalaModule {
    override def configure() {
      install(bindings)
    }

  })

  protected[this] def bindings: Module
}


