package sandbox.util.config

import com.typesafe.config.{ ConfigFactory => TConfigFactory }

/**
 */
object ConfigFactory
{
  private lazy val config = {
    // Load environment specific config ${env}.conf falling back to the default
    // application.conf (and references.conf(s) if they exist in classpath.  This
    // allows ${env}.conf to specify environment specific overrides of the base
    // configuration.
    System.setProperty("config.trace", "loads")
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

