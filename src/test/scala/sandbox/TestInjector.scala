package sandbox

import com.google.inject.{Module, Key, Guice}
import net.codingwell.scalaguice.ScalaModule
import scala.reflect._

trait TestInjector {
  private val injector = Guice.createInjector(new ScalaModule {
    override def configure() {
      install(bindings)
    }
  })

  protected[this] def bindings: Module

  def inject[A <: AnyRef : ClassTag]: A = {
    injector.getInstance(classTag[A].runtimeClass).asInstanceOf[A]
  }

  def inject[A <: AnyRef](key: Key[A]): A = {
    injector.getInstance(key)
  }
}


