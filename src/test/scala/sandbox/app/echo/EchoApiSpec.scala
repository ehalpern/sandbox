package sandbox.app.echo

import sandbox.UnitTestSpec

/**
 * Test the EchoApi in isolation.
 */
class EchoApiSpec extends UnitTestSpec
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