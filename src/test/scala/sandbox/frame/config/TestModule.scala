package sandbox.frame.config

import com.typesafe.config.Config
import net.codingwell.scalaguice.ScalaModule

/**
 */
class TestModule(config: Config) extends ScalaModule with ConfigBindingSupport
{
  def configure {
    bind[TestInjectable]
    bindConfig(config)
  }
}


