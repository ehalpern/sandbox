package twine.app.climate

import org.mockito.Mockito._
import twine.{UnitTestSupport, StandardSpec}
import scala.concurrent.Future
import twine.app.climate.wbclimate.{WbClimateData, WbClimateClient}

/**
 */
class ClimateServiceSpec extends StandardSpec with UnitTestSupport
{
  val TestFromYear = 1980
  val TestToYear = 1999
  val TestLocation = "FJI"

  /**
   * Create the climateService manually and use a mock WbClimateClient
   * instead of the real one.  Note that this is a method so that it can be
   * overridden by the ClimateServiceIntegSpec which extends this class to
   * reuse for integration testing
   */
  def climateService: ClimateService = {
    val mockClient = mock[WbClimateClient]

    when(mockClient.fetchPrecipitationStats(TestLocation, TestFromYear, TestToYear)).thenReturn(
      Future(Seq(WbClimateData.dummyPrecipitationData(TestFromYear, TestToYear)))
    )
    when(mockClient.fetchTemperatureStats(TestLocation, TestFromYear, TestToYear)).thenReturn(
      Future(Seq(WbClimateData.dummyTemperatureData(TestFromYear, TestToYear)))
    )
    new ClimateServiceImpl(mockClient)
  }

  "a query for 20 year average Fiji climate" should {
    "respond with entry containing precipitation and temperature" in {
      whenReady (climateService.query(TestLocation, TestFromYear, TestToYear)) { r =>
        r should have (
          'location (TestLocation),
          'fromYear (TestFromYear),
          'toYear   (TestToYear)
        )
        r.temperature.annual should be > 1.0
        r.precipitation.annual should be > 1.0
      }
    }
  }
}