package sandbox.frame.config

import com.typesafe.config.ConfigValueType._
import com.typesafe.config.{ConfigValue, Config}
import net.codingwell.scalaguice.BindingExtensions._
import net.codingwell.scalaguice.ScalaModule
import scala.collection.JavaConversions._

/**
 * Provides support for binding configuration settings (contained in a typesafe
 * Config object) to @Named annotations so that they can be injected.
 *
 * Example: Consider the following application.conf:
 *
 * {{{
 * host = localhost
 * port = 8080
 * cache {
 *   hosts = [ mem1, mem2, mem3 ]
 *   port = 11211
 * }
 *
 * }}}
 *
 * And the following code:
 *
 * {{{
 * class MyModule extends ScalaModule with ConfigBindingSupport
 * {
 *   val config = ConfigFactory.load
 *
 *   bind[CacheService]
 *   bindConfig(config)
 * }
 * }}}
 *
 * The call to bindConfig binds the values for host, port, cache.hosts and cache.port
 * to @Named annotations using the keys as the annotation values.
 *
 * These values can be injected into any Injectable class:
 *
 * {{{
 * class CacheService @Inject() (
 *   @Named("cache.hosts") hosts: List,
 *   @Named("cache.port")  port: Int
 * ) {
 *   ...
 * }
 * }}}
 *
 * While the use of Strings may first appear fragile, it turns out in practice that any
 * misspelling or reference to an incorrect type will be immediately flagged on startup.
 * So, although the checking is not done at compile time, any error will be flagged
 * immediately on startup.
 */
trait ConfigBindingSupport
{
  module: ScalaModule =>

  /**
   * Binds every entry in Config to a @Named annotation using the fully qualified
   * config key as the name.
   * @param config
   */
  protected def bindConfig(config: Config) {
    for (entry <- config.entrySet) {
      val cv = entry.getValue
      cv.valueType match {
        case STRING | NUMBER | BOOLEAN =>
          bindPrimitive(entry.getKey, entry.getValue)
        case LIST =>
          bindList(entry.getKey, entry.getValue)
        case NULL =>
          throw new AssertionError(
            s"Did not expect NULL entry in ConfigValue.entrySet: ${cv.origin}"
          )
        case OBJECT =>
          throw new AssertionError(
            s"Did not expect OBJECT entry in ConfigValue.entrySet: ${cv.origin}"
          )
      }
    }
  }

  private def bindPrimitive(key: String, value: ConfigValue) {
    val unwrapped = value.unwrapped.toString
    binderAccess.bindConstant.annotatedWithName(key).to(unwrapped)
  }

  private def bindList(key: String, value: ConfigValue) {
    val list = value.unwrapped.asInstanceOf[java.util.List[Any]]
    if (list.size == 0) {
      // Seq[Int|Double|Boolean] type params will only match a value bound as Seq[Any]
      bind[Seq[Any]].annotatedWithName(key).toInstance(Seq())
      // Seq[String] type params will only match a value bound as Seq[String]
      bind[Seq[String]].annotatedWithName(key).toInstance(Seq())
    } else {
      val seq = list.get(0) match {
        case x: Integer =>
          val v = list.collect({case x: java.lang.Integer => x.intValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: Double =>
          val v = list.collect({case x: java.lang.Double => x.doubleValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: Boolean =>
          val v = list.collect({case x: java.lang.Boolean => x.booleanValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: String =>
          val v = list.collect({case x: String => x}).toSeq
          bind[Seq[String]].annotatedWithName(key).toInstance(v)
        case x =>
          throw new AssertionError("Unsupported list type " + x.getClass)
      }
    }
  }
}
