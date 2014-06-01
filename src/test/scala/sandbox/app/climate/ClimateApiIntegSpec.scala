package sandbox.app.climate

import org.scalatest.{Matchers, WordSpec}
import sandbox.IntegTestSupport
import spray.http.StatusCodes
import sandbox.app.climate.model.ClimateQueryResult

/**
 */
class ClimateApiIntegSpec extends WordSpec
  with Matchers
  with IntegTestSupport
  with ClimateApiJsonSupport
{
  "GET /climate" should {
    "respond with success" when {
      "given a valid location and range" in {
        Get("/climate?location=FJI&fromYear=1980&toYear=1999") ~> route ~> check {
          status should be(StatusCodes.OK)
          entityAs[ClimateQueryResult].list should be('nonEmpty)
        }
      }
    }
  }
}