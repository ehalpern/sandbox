package sandbox.app.climate

import sandbox.IntegTestSpec
import spray.http.StatusCodes
import sandbox.app.climate.model.ClimateQueryResult

/**
 */
class ClimateApiIntegSpec extends IntegTestSpec
  with ClimateApiJsonSupport
{
  "GET /climate" should {
    "respond with success" when {
      "given a valid location and range" in {
        Get("/climate?location=FJI&fromYear=1980&toYear=1999") ~> route ~> check {
          status should be(StatusCodes.OK)
          val result = entityAs[ClimateQueryResult]
          result.list should be('nonEmpty)
        }
      }
    }
  }
}