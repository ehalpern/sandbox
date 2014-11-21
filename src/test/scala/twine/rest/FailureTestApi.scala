package twine.rest

import com.typesafe.scalalogging.slf4j.LazyLogging
import spray.routing.Directives

import scala.concurrent.{ExecutionContext, Future}

class FailureTestApi(implicit ec: ExecutionContext)
  extends RestComponent
  with Directives
  with LazyLogging
{
  val service = new FailureTestService

  def route = {
    path("echo") {
      get {
        parameter('msg) { (required) =>
          complete(required)
        }
      }
    } ~
    path("timeout") {
      get {
        complete {
          service.timeout(5000)
        }
      }
    } ~
    path("crash") {
      get {
        complete {
          service.crash("crash boom bang")
        }
      }
    } ~
    path("exception") {
      get {
        parameters('code.?.as[Option[Int]]) { (code) =>
          complete {
            service.fail(
              code match {
                case Some(c) => new ExceptionWithStatus(c, "exceptionWithStatus")
                case None => new Exception("arbitrary exception")
              }
            )
          }
        }
      }
    }
  }
}

class FailureTestService
{
  def fail(th: Throwable)(implicit ec: ExecutionContext): Future[String] = {
    Future {
      throw th
    }
  }

  def crash(message: String)(implicit ec: ExecutionContext): Future[String] = {
    Future {
      sys.error(s"crash: $message")
      message
    }
  }

  def timeout(millis: Long)(implicit ec: ExecutionContext): Future[String] = {
    Future {
      Thread.sleep(millis)
      "succeeded after timeout"
    }
  }
}