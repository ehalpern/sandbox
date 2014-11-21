package twine.rest

import javax.inject.Inject

import akka.actor.Actor
import akka.event.Logging
import com.typesafe.scalalogging.slf4j.LazyLogging
import spray.http.StatusCodes
import spray.routing.{MissingQueryParamRejection, _}
import spray.util.LoggingContext

class RestServiceActor @Inject()(apiSet: Set[RestComponent]) extends Actor with RestService
{
  def actorRefFactory = {
    context
  }
  
  def receive = {
    runRoute(standardRoute)
  }

  protected[this] def apis = apiSet.toSeq
}


trait RestService extends HttpService with LazyLogging
{
  /**
   * @return List of apis to include in route
   */
  protected[this] def apis: Seq[RestComponent]

  /*protected[spray]*/ lazy val standardRoute =
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