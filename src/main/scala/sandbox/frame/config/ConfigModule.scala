package sandbox.frame.config

import net.codingwell.scalaguice.ScalaModule
import com.typesafe.config.Config

/**
 * Loads the typesafe configuration and binds each config key to a ''@Named("<key>"''
 * annotation.  This makes any config value accessible via injection by annotating a
 * constructor parameter with the desired ''@Named("<key>")'' annotation.
 *
 * For example:
 * {{{
 * class ExampleService @Inject()(
 *   @Named("example.timeout") timeout: Int // injected with Config.getInt("example.timeout")
 * ) {
 *   ...
 * }
 * }}}
 *
 * Also binds the active configuration to Config so that injectable classes can
 * obtain the config object directly.
 *
 * For example:
 *
 * {{{
 * class ExampleService @Inject()(
 *   config: Config
 * ) {
 *   timeout = config.get("example.timeout")
 *   ...
 * }
 * }}}
 */
class ConfigModule extends ScalaModule with ConfigBindingSupport
 {
   def configure {
     val config = ConfigFactoryWithEnvironmentOverride.load
     bind[Config].toInstance(config)
     bindConfig(config)
   }
 }

