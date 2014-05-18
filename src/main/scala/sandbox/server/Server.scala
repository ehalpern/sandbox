package sandbox.server

import akka.actor.{ActorSystem, ActorRef}
import akka.io.IO
import spray.can.Http
import javax.inject.{Named, Inject}

class Server @Inject()(
  @Named("server.host") host: String,
  @Named("server.port")  port: Int
) {
  def start(routerActor: ActorRef)(implicit as: ActorSystem) {
    IO(Http) ! Http.Bind(routerActor, host, port = port)
  }
}
