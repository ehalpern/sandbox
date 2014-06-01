package sandbox.app.climate

import sandbox.UnitTestSpec
import org.mockito.Mockito._
import scala.concurrent.Future

/**
 */
class ClimateServiceSpec extends UnitTestSpec
{
  val mockClient = mock[WbClimateClient]
  val climateService = new ClimateServiceImpl(mockClient)

  "query" should {
    "respond with empty list" in {
      when(mockClient.fetchPrecipitationStats("FJI", 1980, 1999)).thenReturn(Future(Nil))
      when(mockClient.fetchTemperatureStats("FJI", 1980, 1999)).thenReturn(Future(Nil))
      whenReady (climateService.query("FJI", 1980, 1999)) { r =>
        r.list should be('nonEmpty)
      }
    }
  }
}