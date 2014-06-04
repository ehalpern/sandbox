package sandbox

import akka.actor.ActorRefFactory
import com.google.inject.{Guice, Provides}
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.concurrent.IntegrationPatience
import sandbox.frame.spray.{ApiRouter, ApiPlugin}
import scala.concurrent.duration._
import spray.testkit._

import scala.reflect._

/**
 * Extends StandardSpec with features to aid integration testing including:
 *
 *   - [[getAppRouteInstance]] method to obtain a fully wired application route
 *     for integrated API testing
 *
 *   - [[getInstance]] method to obtain a fully wired class/trait instance for
 *     integrated service testing
 *
 *   - longer default future and spray timeouts to accommodate the longer wait
 *     times associated with using real remote services
 *
 */
trait IntegTestSupport extends IntegrationPatience {
  suite: StandardSpec =>

  /**
   * An injector that creates the entire integrated object graph
   */
  private val injector = Guice.createInjector(new ScalaModule {
    override def configure() {
      install(new MainModule)
    }

    /**
     * Provides the test entry point for the application router.  The actual
     * application creates the router using the actor
     * [[sandbox.frame.spray.ApiRouterActor]]
     */
    @Provides
    def provideApiRouter(apiSet: Set[ApiPlugin], arf: ActorRefFactory): ApiRouter = {
      new ApiRouter {
        implicit def actorRefFactory = arf

        protected[this] def apis = apiSet.toSeq
      }
    }
  })

  /**
   * Increases the default timeout for routing tests
   */
  protected[this] implicit val routeTestTimeout = {
    RouteTestTimeout(15.seconds)
  }

  /**
   * Retrieves an injected instance of class A
   */
  protected[this] def getInstance[A <: AnyRef : ClassTag]: A = {
    injector.getInstance(classTag[A].runtimeClass).asInstanceOf[A]
  }

  /**
   * Retrieves an injected instance of the main application route
   */
  protected[this] def getAppRouteInstance = {
    val router = getInstance[ApiRouter]
    router.sealRoute(router.standardRoute)
  }
}

