package sandbox

import com.google.inject.{Provides, Module, Key, Guice}
import net.codingwell.scalaguice.ScalaModule
import scala.reflect._
import sandbox.frame.spray.{ApiRouter, ApiPlugin}
import akka.actor.ActorRefFactory

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


