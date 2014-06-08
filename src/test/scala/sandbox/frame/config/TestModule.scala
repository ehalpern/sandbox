package sandbox.frame.config

import com.typesafe.config.Config
import net.codingwell.scalaguice.ScalaModule

/**
 */
class TestModule(config: Config) extends ScalaModule
{
  def configure {
    bind[TestInjectable]
    install(new ConfigModule {
      override def loadConfig() = {
        config
      }
    })
  }
}


