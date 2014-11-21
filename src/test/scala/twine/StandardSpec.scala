package twine

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import scala.concurrent.duration._
import spray.testkit.ScalatestRouteTest

/**
 * Standard base class for tests.  Includes the following features:
 *
 *   - WordSpec style tests with Matcher DSL for assertions
 *
 *   - Support for testing Futures including the useful whenReady construct
 *
 *   - Support for testing spray Routes
 */
class StandardSpec extends WordSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
{
  protected implicit def routeTestTimeout = {
    RouteTestTimeout(1.seconds)
  }
}

