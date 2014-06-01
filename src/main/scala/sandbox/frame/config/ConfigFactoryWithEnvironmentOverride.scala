package sandbox.frame.config

import com.typesafe.config.{ ConfigFactory => TConfigFactory }
import com.typesafe.scalalogging.slf4j.LazyLogging

object ConfigFactoryWithEnvironmentOverride extends ConfigFactoryWithEnvironmentOverride

class ConfigFactoryWithEnvironmentOverride extends LazyLogging
{
  private lazy val config = {
    // Load environment specific config ${env}.conf falling back to the default
    // application.conf (and references.conf(s) if they exist in classpath.  This
    // allows ${env}.conf to specify environment specific overrides of the base
    // configuration.
    val standard = TConfigFactory.load()
    val env = standard.getString("env")
    val custom = TConfigFactory.parseResourcesAnySyntax(standard.getString("env"))
    if (custom.isEmpty) {
      throw new IllegalStateException(
        s"The 'env' system property is set to $env but " +
        s"there's no environment specific configuration defined in ./conf/$env.conf"
      )
    }
    TConfigFactory.defaultOverrides().withFallback(custom).withFallback(standard).resolve()
  }

  def load = config
}

