package sandbox.server

import spray.routing._
import akka.event.Logging
import javax.inject.Inject
import spray.util.LoggingContext
import spray.http.StatusCodes
import com.typesafe.scalalogging.slf4j.LazyLogging

class ApiRouterActor @Inject()(apiSet: Set[ApiPlugin]) extends HttpServiceActor with ApiRouter
{
  def apis = apiSet.toSeq

  def receive = {
    runRoute(route)
  }
}


trait ApiRouter extends HttpService with LazyLogging
{
  def apis: Seq[ApiPlugin]

  // Use the enclosing actor's dispatcher
  implicit def executionContext = {
    actorRefFactory.dispatcher
  }

  // Use the enclosing actor's dispatcher
  implicit def exceptionHandler(implicit log: LoggingContext) = {
    ExceptionHandler {
      case e: ExceptionWithStatus =>
        complete(e.statusCode, e.msg)
    }
  }

  implicit val rejectionHandler = {
    RejectionHandler {
      case MissingQueryParamRejection(param) :: _ =>
        complete(StatusCodes.BadRequest, s"Request is missing required query parameter '$param'")
    }
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