package sandbox.app.echo

import org.scalatest.WordSpec
import spray.testkit.ScalatestRouteTest

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