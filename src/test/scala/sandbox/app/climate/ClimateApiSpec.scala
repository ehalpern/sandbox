package sandbox.app.climate

import org.mockito.Mockito._
import sandbox.app.climate.model.ClimateQueryResult
import sandbox.{UnitTestSupport, StandardSpec}
import scala.concurrent.Future
import spray.http.StatusCodes

/**
 */
class ClimateApiSpec extends StandardSpec with UnitTestSupport
  with ClimateApiJsonSupport
{

  val TestLocation = "FJI"
  val TestFromYear = 1980
  val TestToYear = 1999

  def route = {
    val mockService = mock[ClimateService]
    when(mockService.query(TestLocation, TestFromYear, TestToYear)).thenReturn(
      Future {
        ClimateQueryResult.dummyResult(TestLocation, TestFromYear, TestToYear)
      }
    )
    (new ClimateApi(mockService)).route
  }

  "GET /climate" should {
    "respond with success" when {
      "given a valid location and range" in {
        Get("/climate?location=FJI&fromYear=1980&toYear=1999") ~> route ~> check {
          status should be(StatusCodes.OK)
          val r = responseAs[ClimateQueryResult]
          r.list should be ('nonEmpty)
          val stat = r.list.head
          stat should have (
            'location (TestLocation),
            'fromYear (TestFromYear),
            'toYear   (TestToYear)
          )
          stat.precipitation should be ('defined)
          stat.precipitation.get.annual should be > 1.0
          stat.temperature should be ('defined)
          stat.temperature.get.annual should be > 1.0
        }
      }
    }
  }
}