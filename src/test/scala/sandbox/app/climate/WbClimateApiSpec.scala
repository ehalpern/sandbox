package sandbox.app.climate

import org.scalatest.{WordSpec, Matchers}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import akka.actor.ActorSystem

class WbClimateApiSpec extends WordSpec
  with Matchers
  with ScalaFutures
  with IntegrationPatience // increase request timeouts
{
  val api = new WbClimateApi(
    "http://climatedataapi.worldbank.org/climateweb/rest/v1"
  )(
    ActorSystem("test")
  )

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
