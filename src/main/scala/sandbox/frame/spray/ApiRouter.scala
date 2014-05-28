package sandbox.frame.spray

import spray.routing._
import akka.event.Logging
import javax.inject.Inject
import spray.util.LoggingContext
import spray.http.StatusCodes
import com.typesafe.scalalogging.slf4j.LazyLogging
import spray.routing.MissingQueryParamRejection

class ApiRouterActor @Inject()(apiSet: Set[ApiPlugin]) extends HttpServiceActor with ApiRouter
{
  def receive = {
    runRoute(standardRoute)
  }

  protected[this] def apis = apiSet.toSeq
}


trait ApiRouter extends HttpService with LazyLogging
{
  /**
   * @return List of apis to include in route
   */
  protected[this] def apis: Seq[ApiPlugin]

  // Use the enclosing actor's dispatcher
  protected[this] implicit def executionContext = {
    actorRefFactory.dispatcher
  }

  protected[this] lazy val standardRoute =
    logRequestResponse("MARK", Logging.InfoLevel) {
      handleRejections(rejectionHandler) {
        handleExceptions(exceptionHandler) {
          apis.tail.foldLeft(apis.head.route) { (chain, next) =>
            chain ~ next.route
          }
        }
      }
    }

  /**
   * Override default to respond with 503 rather than 500
   */
  override def timeoutRoute: Route = {
    complete(
      StatusCodes.ServiceUnavailable,
      "The server could not provide a timely response."
    )
  }

  private def exceptionHandler(implicit log: LoggingContext) = {
    ExceptionHandler {
      case e: ExceptionWithStatus =>
        complete(e.statusCode, e.msg)
    }
  }

  private val rejectionHandler = {
    RejectionHandler {
      case MissingQueryParamRejection(param) :: _ =>
        complete(StatusCodes.BadRequest, s"Request is missing required query parameter '$param'")
    }
  }
}