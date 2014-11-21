package twine.app.climate

import org.mockito.Mockito._
import twine.app.climate.model.{ClimateStats, ClimateQueryResult}
import twine.{UnitTestSupport, StandardSpec}
import scala.concurrent.Future
import spray.http.StatusCodes

/**
 * ClimateApi unit tests.
 *
 * Tests run against a route constructed using ClimateApi backed
 * by a mock ClimateService.  This class is designed to be extended
 * by the ClimateApiIntegTest which replaces the mock implementation
 * of route with he real implementation.
 */
class ClimateApiSpec extends StandardSpec with UnitTestSupport
  with ClimateApiJsonSupport
{
  val TestLocation = "FJI"
  val TestFromYear = 1980
  val TestToYear = 1999

  /**
   * Route to test against.  May be overridden by subclasses to
   * test against an alternate implementation of the router
   */
  protected def testRoute = {
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
        Get("/climate?location=FJI&fromYear=1980&toYear=1999") ~> testRoute ~> check {
          status should be(StatusCodes.OK)
          val r = responseAs[ClimateStats]
          r should have (
            'location (TestLocation),
            'fromYear (TestFromYear),
            'toYear   (TestToYear)
          )
          r.precipitation.annual should be > 1.0
          r.temperature.annual should be > 1.0
        }
      }
    }
  }
}