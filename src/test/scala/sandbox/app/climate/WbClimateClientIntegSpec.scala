package sandbox.app.climate

import org.scalatest.{WordSpec, Matchers}
import sandbox.IntegTestSupport

class WbClimateClientIntegSpec extends WordSpec
  with Matchers
  with IntegTestSupport
  with WbClimateClient.JsonProtocol
{
  val api = inject[WbClimateClient]

  "fetchTemperatureStats" should {
    "return stats" when {
      "given valid arguments" in {
        val result = api.fetchTemperatureStats("FJI", 1980, 1999)
        whenReady(result) { r =>
          r should be ('nonEmpty)
        }
      }
    }
  }

  "fetchPrecipitationStats" should {
    "return stats" when {
      "given valid arguments" in {
        val result = api.fetchPrecipitationStats("FJI", 1980, 1999)
        whenReady(result) { r =>
          r should be ('nonEmpty)
        }
      }
    }
  }
}
