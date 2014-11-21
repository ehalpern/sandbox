package twine.config

import scala.collection.JavaConversions._
import org.scalatest.{Matchers, FunSpec}
import com.google.inject.Guice
import com.typesafe.config.{ConfigFactory, Config}

class ConfigModuleSpec extends FunSpec with Matchers
{
  def loadConfig(fileName: String) = {
    System.setProperty("config.trace", "loads")
    ConfigFactory.load(fileName)
  }

  def getExpectedValues(config: Config) = {
    TestInjectable.Params(
      config.getInt("test.int"),
      config.getDouble("test.double"),
      config.getBoolean("test.boolean"),
      config.getStringList("test.stringList"),
      config.getIntList("test.intList").map(_.intValue).toSeq,
      config.getDoubleList("test.doubleList").map(_.doubleValue).toSeq,
      config.getBooleanList("test.booleanList").map(_.booleanValue).toSeq
    )
  }

  describe ("A module that injects config bindings") {
    val config = loadConfig("all-types.conf")
    val injector = Guice.createInjector(new TestModule(config))

    it ("Should successfully instantiate a class with @Named constructor parameters") {

      val expected = getExpectedValues(config)
      val instance = injector.getInstance(classOf[TestInjectable])

      instance.params equals expected
    }
  }

  describe ("A config that contains empty lists should also bind") {
    val config = loadConfig("empty-lists.conf")
    val injector = Guice.createInjector(new TestModule(config))

    it ("Should successfully instantiate a class with @Named constructor parameters") {

      val expected = getExpectedValues(config)
      val instance = injector.getInstance(classOf[TestInjectable])

      instance.params equals expected
    }
  }

}
