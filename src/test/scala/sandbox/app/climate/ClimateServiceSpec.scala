package sandbox.app.climate

import org.mockito.Mockito._
import sandbox.{UnitTestSupport, StandardSpec}
import scala.concurrent.Future

/**
 */
class ClimateServiceSpec extends StandardSpec with UnitTestSupport
{
  val TestFromYear = 1980
  val TestToYear = 1999
  val TestLocation = "FJI"

  /**
   * Create the climateService manually and inject use a mock WbClimateClient
   * instead of the real one.  Note that this is a method so that it can be
   * overridden by the ClimateServiceIntegSpec which extends this class to reuse
   * the reuse these test during integration testing
   */
  def climateService: ClimateService = {
    val mockClient = mock[WbClimateClient]

    when(mockClient.fetchPrecipitationStats(TestLocation, TestFromYear, TestToYear)).thenReturn(
      Future(Seq(WbClimateClient.testPrecipitationData(TestFromYear, TestToYear)))
    )
    when(mockClient.fetchTemperatureStats(TestLocation, TestFromYear, TestToYear)).thenReturn(
      Future(Seq(WbClimateClient.testTemperatureData(TestFromYear, TestToYear)))
    )
    new ClimateServiceImpl(mockClient)
  }

  "a query for 20 year average Fiji climate" should {
    "respond with entry containing precipitation and temperature" in {
      whenReady (climateService.query(TestLocation, TestFromYear, TestToYear)) { r =>
        r.list should be ('nonEmpty)
        val entry = r.list.head
        entry should have (
          'location (TestLocation),
          'fromYear (TestFromYear),
          'toYear   (TestToYear)
        )
        entry.temperature should be ('defined)
        entry.temperature.get.annual should be > 1.0
        entry.precipitation should be ('defined)
        entry.precipitation.get.annual should be > 1.0
      }
    }
  }
}