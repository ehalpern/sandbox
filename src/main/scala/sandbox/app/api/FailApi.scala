package sandbox.app.api

import spray.routing.Directives

import javax.inject.Inject
import scala.util.{Failure, Success}
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.concurrent.ExecutionContext
import sandbox.app.core.FailService
import sandbox.server.{ExceptionWithStatus, ApiPlugin}
import spray.http.StatusCodes

class FailApi @Inject()(test: FailService)
  extends ApiPlugin
  with Directives
  with LazyLogging
{
  def route(implicit ec: ExecutionContext) = {
    pathPrefix("fail") {
      path("timeout") {
        get {
          complete {
            test.timeout(5000)
          }
        }
      } ~
      path("crash") {
        get {
          complete {
            test.crash("crash boom bang")
          }
        }
      } ~
      path("ex") {
        get {
          complete {
            test.fail(
              new ExceptionWithStatus(StatusCodes.EnhanceYourCalm.intValue,
                "generated exception")
            )
          }
        }
      }
    }
  }
}