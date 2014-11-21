package twine.rest

import spray.routing.Route

trait RestComponent
{
  def route: Route
}