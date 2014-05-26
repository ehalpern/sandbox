package sandbox.util.guice

import akka.actor._
import com.google.inject.Injector
import scala.reflect._

/**
 * Provides an akka extension for instantiating guice injectable actors.  This singleton
 * implements the interface required to plug the extension into akka. GuiceAkkaExtensionImpl
 * implements the extension methods.
 */
object GuiceAkkaExtension extends ExtensionId[GuiceAkkaExtensionImpl] with ExtensionIdProvider
{
  /**
   * @return the extension id of this provider
   */
  override def lookup() = {
    GuiceAkkaExtension
  }

  /**
   * Create a new extension instance
   */
  override def createExtension(system: ExtendedActorSystem) = {
    new GuiceAkkaExtensionImpl
  }
}

class GuiceAkkaExtensionImpl extends Extension
{
  private var injector: Injector = _

  /**
   * Initializes the extension with the guice injector.  This must be called before
   * the extension can be used.
   *
   * This method is invoked indirectly using
   * {{{
   *   GuiceAkkaExtension(system).initialize(injector))
   * }}}
   * @param injector
   */
  def initialize(injector: Injector) {
    this.injector = injector
  }

  /**
   * Returns a Props object that specifies the producer class and parameters required
   * to instantiate the given actor using the GuiceActorProducer.
   *
   * This method is invoked indirectly using
   * {{{
   *   GuiceAkkaExtension(system).props[MyActor]
   * }}}
   */
  def props[A <: Actor : ClassTag] = {
    val producerClz = classTag[GuiceActorProducer[A]].runtimeClass
    val actorClz = classTag[A].runtimeClass
    Props(producerClz, injector, actorClz)
  }
}

