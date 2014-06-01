package sandbox

import org.scalatest.Suite
import spray.testkit.ScalatestRouteTest
import sandbox.frame.spray.{ApiRouter, ApiRouterActor}
import scala.concurrent.duration._
import com.google.inject.name.Names
import akka.actor.ActorRef
import com.google.inject.{Module, Key}
import net.codingwell.scalaguice.ScalaModule
import sandbox.frame.config.ConfigModule
import sandbox.frame.akka.AkkaModule

/**
 */
trait UnitTestInjector extends TestInjector
{
  final protected[this] def bindings = new ScalaModule {
    override def configure() {
      install(new ConfigModule)
      install(new AkkaModule)
    }
  }

  protected[this] def testBindings: Module
}