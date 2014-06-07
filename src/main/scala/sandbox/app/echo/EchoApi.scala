package sandbox.app.echo

import spray.routing.Directives

import javax.inject.Inject
import com.typesafe.scalalogging.slf4j.LazyLogging
import sandbox.frame.spray.ApiComponent

class EchoApi @Inject()
  extends ApiComponent
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