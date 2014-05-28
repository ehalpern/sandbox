package sandbox.frame.spray

import spray.routing.Route
import scala.concurrent.ExecutionContext

trait ApiPlugin
{
  def route(implicit ec: ExecutionContext): Route
}