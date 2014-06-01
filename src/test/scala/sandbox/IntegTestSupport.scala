package sandbox

import org.scalatest.Suite
import spray.testkit.ScalatestRouteTest
import sandbox.frame.spray.{ApiRouter, ApiRouterActor}
import scala.concurrent.duration._
import com.google.inject.name.Names
import akka.actor.ActorRef
import com.google.inject.Key
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}

/**
 */
trait IntegTestSupport extends ScalatestRouteTest
  with IntegTestInjector
  with ScalaFutures        // include futures testing support
  with IntegrationPatience // increase futures timeouts
{
  suite: Suite =>

  private val apiRouter = inject[ApiRouter]

  protected[this] val route = apiRouter.standardRoute
  protected[this] implicit val routeTestTimeout = RouteTestTimeout(15.seconds)
}