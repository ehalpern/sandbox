package twine.rest

import javax.inject.{Inject, Named}

import akka.actor.{ActorRef, ActorSystem}
import akka.io.IO
import spray.can.Http

class RestServer @Inject()(
  @Named("server.host")       host: String,
  @Named("server.port")       port: Int,
  @Named("ApiRouterActorRef") routerRef: ActorRef
) (
  implicit actorSystem: ActorSystem
) {
  def start {
    IO(Http) ! Http.Bind(routerRef, host, port = port)
  }
}
