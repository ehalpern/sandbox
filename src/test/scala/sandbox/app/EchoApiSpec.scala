package sandbox.app

import org.scalatest.FunSpec
import spray.testkit.ScalatestRouteTest
import sandbox.app.api.EchoApi

/**
 */
class EchoApiSpec extends FunSpec
  with ScalatestRouteTest
{
  def actorRefFactory = system

  val route = (new EchoApi).route

  describe("GET /echo?msg=foo") {
    it("should response with foo") {
      Get("/echo?msg=foo") ~> route ~> check {
        responseAs[String] === "foo"
      }
    }
  }
}