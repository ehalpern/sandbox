package sandbox

import org.scalatest.{WordSpec, Matchers}
import spray.testkit.ScalatestRouteTest
import org.scalatest.mock.MockitoSugar
import org.scalatest.concurrent.ScalaFutures
import net.codingwell.scalaguice.ScalaModule
import sandbox.frame.config.ConfigModule
import sandbox.frame.akka.AkkaModule

/**
 * Standard base for isolated unit tests.
 *
 * Tests extending this class will inherit the following features:
 *   - WordSpec style tests with Matcher DSL for assertions
 *   - Support for testing Futures including the useful whenReady construct
 *   - Support for testing spray Routes
 *   - Support for mockito mocking
 *   - Defines implicits required for future, akka and spray route testing
 */
class UnitTestSpec extends WordSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with MockitoSugar
  with TestInjector
{
  final protected[this] def bindings = new ScalaModule {
    override def configure() {
      install(new ConfigModule)
      install(new AkkaModule)
    }
  }
}
