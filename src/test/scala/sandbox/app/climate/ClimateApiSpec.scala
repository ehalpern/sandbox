package sandbox.app.climate

import org.scalatest.{Matchers, WordSpec}
import sandbox.UnitTestSpec
import spray.http.StatusCodes
import sandbox.app.climate.model.{ClimateStats, ClimateQueryResult}
import scala.concurrent.Future
import org.mockito.Mockito._

/**
 */
class ClimateApiSpec extends UnitTestSpec
  with ClimateApiJsonSupport
{
  val mockService = mock[ClimateService]

  val route = (new ClimateApi(mockService)).route

  "GET /climate" should {
    "respond with success" when {
      "given a valid location and range" in {
        when(mockService.query("FJI", 1980, 1999)).thenReturn(
          Future {
            ClimateQueryResult(List(ClimateStats("FJI", 1980, 1999)))
          }
        )

        Get("/climate?location=FJI&fromYear=1980&toYear=1999") ~> route ~> check {
          status should be(StatusCodes.OK)
          entityAs[ClimateQueryResult].list should be('nonEmpty)
        }
      }
    }
  }
}