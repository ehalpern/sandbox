package sandbox.app.api

import spray.routing.Directives

import javax.inject.Inject
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.concurrent.ExecutionContext
import sandbox.server.ApiPlugin

class EchoApi @Inject()
  extends ApiPlugin
  with Directives
  with LazyLogging
{
  def route(implicit ec: ExecutionContext) = {
    path("echo") {
      get {
        parameter('msg) { (message) =>
          complete(message)
        }
      }
    }
  }
}