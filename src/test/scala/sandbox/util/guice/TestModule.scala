package sandbox.util.guice

import sandbox.core.{ExampleApi, ExampleServiceImpl, ExampleService}
import sandbox.util.config.ConfigFactory
import net.codingwell.scalaguice.ScalaModule
import com.typesafe.config.Config

/**
 */
class TestModule(config: Config) extends ScalaModule with ConfigBindingSupport
{
  def configure {
    bind[TestInjectable]
    bindConfig(config)
  }
}


