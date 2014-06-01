package sandbox

import akka.actor.ActorRefFactory
import com.google.inject.Provides
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{Matchers, WordSpec}
import sandbox.frame.spray.{ApiPlugin, ApiRouter}
import scala.concurrent.duration._
import spray.testkit.ScalatestRouteTest

/**
 * Standard base for integration tests.
 *
 * Tests extending this class will inherit the following features:
 *   - WordSpec style tests with Matcher DSL for assertions
 *   - Support for testing Futures including the useful whenReady construct
 *   - Support for testing spray Routes
 *   - Longer future and spray times since services we're using
 *     real remote services
 *   - Defines implicits required for future, akka and spray route testing
 *   - Access to all of the applications apis and services (which
 *     are created using dependency injection).  You can write an api
 *     tests against any api defined by the app without additional wiring.
 *     You can also obtain any arbitrary service using
 *     {{{ val service = inject[ServiceInterface]}}}.
 */
class IntegTestSpec extends WordSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with IntegrationPatience
  with TestInjector
{
  /**
   * Install everything
   */
  final override protected[this] def bindings = new ScalaModule {
    def configure() = {
      install(new MainModule)
    }

    /**
     * Use this for the test entry point (instead of ApiRouterActor which
     * is the entry point when running as a server).
     */
    @Provides
    def provideApiRouter(apiSet: Set[ApiPlugin], arf: ActorRefFactory): ApiRouter = {
      new ApiRouter {
        implicit def actorRefFactory = arf
        protected[this] def apis = apiSet.toSeq
      }
    }
  }

  /**
   * Route spray route to be used for api testing
   */
  protected[this] val route = inject[ApiRouter].standardRoute

  /**
   * Increase timeout for routing tests since they may depend on external
   * services.
   */
  protected[this] implicit val routeTestTimeout = RouteTestTimeout(15 seconds)
}

