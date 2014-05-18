package sandbox.app.core

import javax.inject.{Named, Inject}
import scala.concurrent._
import com.typesafe.scalalogging.slf4j.LazyLogging
import sandbox.server.ExceptionWithStatus

/**
 */
trait FailService {
  def fail(throwable: Throwable)(implicit ec: ExecutionContext): Future[String]
  def crash(message: String)(implicit ec: ExecutionContext): Future[String]
  def timeout(millis: Long)(implicit ec: ExecutionContext): Future[String]
}

class FailServiceImpl @Inject()
  extends FailService with LazyLogging
{
  def fail(th: Throwable)(implicit ec: ExecutionContext) = {
    Future {
      throw th
    }
  }

  def crash(message: String)(implicit ec: ExecutionContext) = {
    Future {
      sys.error(s"crash: $message")
      message
    }
  }

  def timeout(millis: Long)(implicit ec: ExecutionContext) = {
    Future {
      Thread.sleep(millis)
      "succeeded after timeout"
    }
  }
}
