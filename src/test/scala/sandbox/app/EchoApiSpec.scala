package sandbox.app

import org.scalatest.{WordSpec, FunSpec}
import spray.testkit.ScalatestRouteTest
import sandbox.app.api.EchoApi

/**
 */
class EchoApiSpec extends WordSpec
  with ScalatestRouteTest
{
  def actorRefFactory = system

  val route = (new EchoApi).route

  "GET /echo?msg=foo" should {
    "respond with foo" in {
      Get("/echo?msg=foo") ~> route ~> check {
        responseAs[String] === "foo"
      }
    }
  }
}