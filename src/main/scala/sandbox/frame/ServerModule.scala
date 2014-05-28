package sandbox.frame

import net.codingwell.scalaguice.ScalaModule
import sandbox.frame.spray.SprayModule
import sandbox.app.ApisModule

/**
 * Adds HTTP server functionality on top of core.  Includes spay and all
 * apis registered in ApisModule.
 */
class ServerModule extends ScalaModule
 {
   def configure {
     install(new SprayModule)
     install(new ApisModule)
   }
 }

