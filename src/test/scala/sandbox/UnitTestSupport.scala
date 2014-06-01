package sandbox

import org.scalatest.Suite
import spray.testkit.ScalatestRouteTest
import sandbox.frame.spray.ApiRouter
import scala.concurrent.duration._
import com.google.inject.Key

/**
 */
trait UnitTestSupport extends UnitTestInjector
{
  suite: Suite =>
}