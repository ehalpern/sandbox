package sandbox.core

import spray.routing.Route
import scala.concurrent.ExecutionContext

trait ApiRoute
{
  def route(implicit ec: ExecutionContext): Route
}