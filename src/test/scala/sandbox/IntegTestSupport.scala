package sandbox

import akka.actor.ActorRefFactory
import com.google.inject.{Guice, Key, Provides}
import net.codingwell.scalaguice.ScalaModule
import sandbox.frame.spray.{ApiRouter, ApiPlugin}
import scala.concurrent.duration._
import scala.reflect._
import org.scalatest.concurrent.IntegrationPatience
import spray.routing.HttpService

/**
 * Extends StandardSpec with features to aid in integration testing including:
 *   - Longer future and spray timeouts since services we're using
 *     real remote services
 *   - Implicits required for future, akka and spray route testing
 *   - Access to all of the applications apis and services (which
 *     are created using dependency injection).  You can write an api
 *     tests against any api defined by the app without additional wiring.
 *     You can also obtain any arbitrary service using
 *     {{{ val service = inject[ServiceInterface]}}}.
 */
trait IntegTestSupport extends IntegrationPatience
{
  suite: StandardSpec =>

  /**
   * Install everything
   */
  private val injector = Guice.createInjector(new ScalaModule {
    override def configure() {
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
  })

  protected[this] def inject[A <: AnyRef : ClassTag]: A = {
    injector.getInstance(classTag[A].runtimeClass).asInstanceOf[A]
  }

  protected[this] def inject[A <: AnyRef](key: Key[A]): A = {
    injector.getInstance(key)
  }

  /**
   * Route spray route to be used for api testing
   */
  protected[this] def route = {
    val router = inject[ApiRouter]
    router.sealRoute(router.standardRoute)
  }

  /**
   * Increase timeout for routing tests since they may depend on external
   * services.
   */
  protected[this] implicit val routeTestTimeout = RouteTestTimeout(15.seconds)
}

