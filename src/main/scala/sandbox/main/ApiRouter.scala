package sandbox.main

import spray.routing.{HttpServiceActor, HttpService}
import akka.event.Logging
import javax.inject.Inject
import sandbox.core.ApiRoute

class ApiRouterActor @Inject()(apiSet: Set[ApiRoute]) extends HttpServiceActor with ApiRouter
{
  def apis = apiSet.toSeq

  def receive = {
    runRoute(route)
  }
}

trait ApiRouter extends HttpService
{
  def apis: Seq[ApiRoute]

  // Use the enclosing actor's dispatcher
  implicit def executionContext = {
    actorRefFactory.dispatcher
  }

  lazy val route =
    logRequestResponse("MARK", Logging.InfoLevel) {
      apis.tail.foldLeft(apis.head.route) { (chain, next) =>
        chain ~ next.route
      }
    }

  def apiRoutes = {
    route
  }
}