package twine

import _root_.akka.actor.ActorRefFactory
import com.google.inject.{Guice, Provides}
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.concurrent.IntegrationPatience
import twine.rest.{RestService, RestComponent}
import scala.concurrent.duration._

import scala.reflect._

/**
 * Extends StandardSpec with features to aid integration testing including:
 *
 *   - [[getRestServiceInstance]] method to obtain a fully wired application route
 *     for integrated API testing
 *
 *   - [[getInstance]] method to obtain a fully wired class/trait instance for
 *     integrated service testing
 *
 *   - longer default future and spray timeouts to accommodate the longer wait
 *     times associated with using real remote services
 *
 */
trait IntegTestSupport extends IntegrationPatience
{
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
     * [[twine.rest.RestServiceActor]]
     */
    @Provides
    def provideApiRouter(apiSet: Set[RestComponent], arf: ActorRefFactory): RestService = {
      new RestService {
        implicit def actorRefFactory = arf

        protected[this] def apis = apiSet.toSeq
      }
    }
  })

  /**
   * Increases the default timeout for routing tests
   */
  override implicit protected def routeTestTimeout: RouteTestTimeout = {
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
  protected[this] def getRestServiceInstance = {
    val router = getInstance[RestService]
    router.sealRoute(router.standardRoute)
  }
}

