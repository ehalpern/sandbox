/**
 * SBT extensions that can't be expressed in .sbt file
 */
import sbt._
import Keys._

object ExtendedBuild extends Build {
  lazy val root = Project("root", file("."))
    /**
     * Separate unit tests, integ tests and e2e tests.
     * - 'sbt test' runs unit tests (*Spec but not *IntegSpec)
     * - 'sbt integ:test' runs integ tests (*IntegSpec)
     * @see http://www.scala-sbt.org/release/docs/Detailed-Topics/Testing#additional-test-configurations-with-shared-sources
     */
    .configs(IntegTest)
    .settings(inConfig(IntegTest)(Defaults.testTasks) : _*)
    .settings(
      testOptions in Test := Seq(
        Tests.Filter(unitFilter),
        // Put results in target/test-reports
        Tests.Argument(TestFrameworks.ScalaTest, "-o", "-u", "target/test-reports")
      ),
      testOptions in IntegTest := Seq(
        Tests.Filter(integFilter),
        // Put results in target/test-reports
        Tests.Argument(TestFrameworks.ScalaTest, "-oF", "-u", "target/test-reports")
      )
    )

  def integFilter(name: String): Boolean = name endsWith "IntegSpec"
  def unitFilter(name: String): Boolean = (name endsWith "Spec") && !integFilter(name)

  lazy val IntegTest = config("integ") extend(Test)
}
