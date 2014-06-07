package sandbox.frame.spray

import spray.routing.Route

trait ApiComponent
{
  def route: Route
}