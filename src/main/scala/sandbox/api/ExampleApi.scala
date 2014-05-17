package sandbox.api

import spray.routing.Directives

import javax.inject.Inject
import sandbox.core.ExampleService
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext
import com.typesafe.scalalogging.slf4j.LazyLogging

class ExampleApi @Inject()(test: ExampleService) extends Directives with LazyLogging
{
  def routes(implicit ec: ExecutionContext) = {
    path("success") {
      get {
        onComplete(test.succeed("Success!")) {
          case Success(value) => complete(value)
          case Failure(ex)    => throw ex
        }
      }
    } ~
    path("timeout") {
      get {
        ctx =>
        // we simply let the request drop to provoke a timeout
      }
    } ~
    path("crash") {
      get {
        onComplete(test.crash("crash boom bang")) {
          case Success(value) => complete(value)
          case Failure(ex)    => throw ex
        }
      }
    } ~
    path("fail") {
      get {
        onComplete(test.fail(400)) {
          case Success(value) =>
            complete(value)
          case Failure(ex)    =>
            logger.debug(s"throwing $ex")
            throw ex
        }
      }
    } ~
    path("exception") {
      get {
        complete {
          throw new RuntimeException("exception")
        }
      }
    }
  }
}