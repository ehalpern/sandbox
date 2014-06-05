package sandbox

import org.scalatest.mock.MockitoSugar
import scala.concurrent.duration._

/**
 * Extends StandardSpec with features to aid unit testing including:
 *   - Support for mockito mocking
 *   - Defines implicits required for future, akka and spray route testing
 */
trait UnitTestSupport extends MockitoSugar
{
  suite: StandardSpec =>

  override protected implicit def routeTestTimeout: RouteTestTimeout = {
    RouteTestTimeout(1.seconds)
  }
}
