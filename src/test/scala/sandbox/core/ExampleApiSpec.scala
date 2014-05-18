package sandbox.core

import org.scalatest.{WordSpec, Matchers}
import sandbox.main.ApiRouter
import spray.testkit.ScalatestRouteTest

/**
 */
class ExampleApiSpec extends WordSpec
  with ApiRouter
  with ScalatestRouteTest
  with Matchers
{
  def actorRefFactory = system

  def apis = {
    Seq(new ExampleApi(new ExampleServiceImpl(0, "")))
  }

  /*
  "The service" should {
    "return success! for GET /success" in {
      Get("/success") ~> apiRoutes ~> check {
        responseAs[String] === "Success!"
      }
    }
  }
  */
}