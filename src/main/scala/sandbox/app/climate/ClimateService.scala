package sandbox.app.climate

import javax.inject.Inject
import sandbox.app.climate.model.ClimateStats
import sandbox.app.climate.wbclimate.WbClimateClient
import scala.concurrent._

/**
 */
trait ClimateService
{
  def query(location: String, fromYear: Int, toYear: Int): Future[Seq[ClimateStats]]
}

class ClimateServiceImpl @Inject()(wbClient: WbClimateClient)
                                  (implicit ec: ExecutionContext)
  extends ClimateService
{
  def query(location: String, fromYear: Int, toYear: Int)
  = {
    (for {
      temp <- wbClient.fetchTemperatureStats(location, fromYear, toYear)
      rain <- wbClient.fetchPrecipitationStats(location, fromYear, toYear)
    } yield {
      ClimateStats.fromData(location, fromYear, toYear, temp, rain)
    }) map { stats =>
      Seq(stats)
    }
  }
}
