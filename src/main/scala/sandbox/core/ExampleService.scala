package sandbox.core

import javax.inject.{Named, Inject}
import scala.concurrent._
import com.typesafe.scalalogging.slf4j.LazyLogging

/**
 */
trait ExampleService {
  def succeed(message: String)(implicit ec: ExecutionContext): Future[String]
  def fail(status: Int)(implicit ec: ExecutionContext): Future[String]
  def crash(message: String)(implicit ec: ExecutionContext): Future[String]
}

class ExampleServiceImpl @Inject()(
  @Named("example.number") num: Int,
  @Named("example.string") str: String
  //@Named("example.list")   list: List[String]
) extends ExampleService with LazyLogging
{
  def succeed(message: String)(implicit ec: ExecutionContext) = {
    Future {
      logger.info(s"\tnum: $num\n\tstr: $str")
      message
    }
  }

  def fail(status: Int)(implicit ec: ExecutionContext) = {
    Future {
      logger.debug("prepare to fail")
      throw new RuntimeException(s"status: $status")
    }
  }

  def crash(message: String)(implicit ec: ExecutionContext) = {
    Future {
      sys.error(s"crash: $message")
      message
    }
  }
}
