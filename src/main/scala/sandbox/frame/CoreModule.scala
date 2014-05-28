package sandbox.frame

import net.codingwell.scalaguice.ScalaModule
import sandbox.frame.akka.AkkaModule
import sandbox.frame.config.ConfigModule
import sandbox.app.ServicesModule

/**
 * Core system module.  Includes configuration, akka and application
 * services.
 */
class CoreModule extends ScalaModule
 {
   def configure {
     install(new ConfigModule)
     install(new AkkaModule)
     install(new ServicesModule)
   }
 }

