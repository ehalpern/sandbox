package sandbox.server

import akka.actor.{ActorSystem, ActorRef}
import akka.io.IO
import spray.can.Http
import javax.inject.{Named, Inject}

class Server @Inject()(
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
