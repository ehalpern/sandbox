package twine.app.echo

import spray.routing.Directives

import javax.inject.Inject
import com.typesafe.scalalogging.slf4j.LazyLogging
import twine.rest.RestComponent

class EchoApi @Inject()
  extends RestComponent
  with Directives
  with LazyLogging
{
  def route = {
    path("echo") {
      get {
        parameter('msg) { (message) =>
          complete(message)
        }
      }
    }
  }
}