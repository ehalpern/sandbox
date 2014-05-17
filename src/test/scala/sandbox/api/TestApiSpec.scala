package sandbox.api

import spray.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import sandbox.main.ApiRouter
import sandbox.core.ExampleServiceImpl

/**
 */
class TestApiSpec extends WordSpec
  with ApiRouter
  with ScalatestRouteTest
  with Matchers
{
  def actorRefFactory = system

  def apiRouters = {
    Map("test" -> new ExampleApi(new ExampleServiceImpl(0, "")))
  }


  "The service" should {
    "return success! for GET /success" in {
      Get("/success") ~> apiRoutes ~> check {
        responseAs[String] === "Success!"
      }
    }
  }
}