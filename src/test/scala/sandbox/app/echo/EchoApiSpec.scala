package sandbox.app.echo

import sandbox.{StandardSpec, UnitTestSupport}

/**
 * Test the EchoApi in isolation.
 */
class EchoApiSpec extends StandardSpec with UnitTestSupport
{
  val route = (new EchoApi).route

  "GET /echo?msg=foo" should {
    "respond with foo" in {
      Get("/echo?msg=foo") ~> route ~> check {
        responseAs[String] === "foo"
      }
    }
  }
}