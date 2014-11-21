package twine.app.climate

import javax.inject.Inject
import twine.app.climate.model.ClimateStats
import twine.app.climate.wbclimate.WbClimateClient
import scala.concurrent._

/**
 */
trait ClimateService
{
  def query(location: String, fromYear: Int, toYear: Int): Future[ClimateStats]
}

class ClimateServiceImpl @Inject()(wbClient: WbClimateClient)
                                  (implicit ec: ExecutionContext)
  extends ClimateService
{
  def query(location: String, fromYear: Int, toYear: Int)
  = {
    // Only allow one period for now.  Longer periods should return
    // multiple results in the future
    val (from, to) = WbClimateClient.closestPeriod(fromYear, toYear)
    for {
      temp <- wbClient.fetchTemperatureStats(location, from, to)
      rain <- wbClient.fetchPrecipitationStats(location, from, to)
    } yield {
      // The temp and rain data is a list of estimates based on different models.
      // We just use the first model for now.
      ClimateStats.fromData(location, from, to, temp.head, rain.head)
    }
  }
}
