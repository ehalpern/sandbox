package sandbox.app.climate

import org.scalatest.{Matchers, WordSpec}
import spray.testkit.ScalatestRouteTest
import sandbox.util.config.ConfigFactory
import spray.http.StatusCodes
import scala.concurrent.duration._
import sandbox.app.climate.model.ClimateQueryResult

/**
 */
class ClimateApiIntegSpec extends WordSpec
  with Matchers
  with ScalatestRouteTest
  with ClimateApiJsonSupport
{
  def actorRefFactory = system

  implicit val routeTestTimeout = RouteTestTimeout(15.second)

  val api = new ClimateApi(
    new ClimateServiceImpl(
      new WbClimateClient(
        ConfigFactory.load.getString("climate.api.endpoint")
      )
    )
  )
  val route = api.route

  "GET /climate" should {
    "respond with success" when {
      "given a valid location and range" in {
        Get("/climate?location=FJI&fromYear=1980&toYear=1999") ~> route ~> check {
          status should be(StatusCodes.OK)
          entityAs[ClimateQueryResult].list should be ('nonEmpty)
        }
      }
    }
  }
}