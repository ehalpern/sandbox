package sandbox.main

import spray.routing.{HttpServiceActor, HttpService}
import akka.event.Logging
import sandbox.api.ExampleApi
import javax.inject.Inject

class ApiRouterActor @Inject()(exampleApi: ExampleApi) extends HttpServiceActor with ApiRouter
{
  def receive = {
    runRoute(apiRoutes)
  }

  def apiRouters = {
    Map("example" -> exampleApi)
  }
}

trait ApiRouter extends HttpService
{
  // Use the enclosing actor's dispatcher
  implicit def executionContext = {
    actorRefFactory.dispatcher
  }

  def apiRoutes = {
    logRequestResponse("MARK", Logging.InfoLevel) {
      pathPrefix(apiRouters.head._1) {
        apiRouters.head._2.routes
      }
    }
  }

  def apiRouters(): Map[String, ExampleApi]
}