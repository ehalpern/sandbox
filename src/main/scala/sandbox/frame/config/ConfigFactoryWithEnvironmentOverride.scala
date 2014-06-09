package sandbox.frame.config

import com.typesafe.config.ConfigFactory

/**
 * Extends standard ConfigFactory to also load an environment specific
 * configuration defined in an 'env'.conf file.  See
 * [[ConfigFactoryWithEnvironmentOverride.load]] for details.
 */
object ConfigFactoryWithEnvironmentOverride
{
  private lazy val config = {
    // Load environment specific config ${env}.conf falling back to the default
    // application.conf (and references.conf(s) if they exist in classpath.  This
    // allows ${env}.conf to specify environment specific overrides of the base
    // configuration.
    val standard = ConfigFactory.load()
    val env = standard.getString("env")
    val custom = ConfigFactory.parseResourcesAnySyntax(env)
    if (custom.isEmpty) {
      throw new IllegalStateException(
        s"The 'env' system property is set to $env but " +
        s"there's no environment specific configuration defined in ./conf/$env.conf"
      )
    }
    ConfigFactory.defaultOverrides().withFallback(custom).withFallback(standard).resolve()
  }

  /**
   * Load the configuration.  Uses the same rules as [[ConfigFactory.load]] to find
   * configuration files.  If the 'env' system property is set, it will additionally
   * load 'env.conf' to include any environment specific overrides.
   *
   * For example, you could define conf/live.conf for production sepcific overrides
   * and use -Denv=live when starting the production server
   */
  def load = config
}

