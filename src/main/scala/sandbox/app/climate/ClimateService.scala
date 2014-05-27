package sandbox.app.climate

import javax.inject.Inject
import scala.concurrent._
import com.typesafe.scalalogging.slf4j.LazyLogging
import sandbox.app.climate.model.{ClimateStats, ClimateQueryResult}

/**
 */
trait ClimateService {
  def query(location: String, fromYear: Int, toYear: Int)
  : Future[ClimateQueryResult]
}

class ClimateServiceImpl @Inject()(
  wbClient: WbClimateClient
)(
  implicit ec: ExecutionContext
)
extends ClimateService with LazyLogging
{
  def query(location: String, fromYear: Int, toYear: Int)
  = {
    (for {
      temp <- wbClient.fetchTemperatureStats(location, fromYear, toYear)
      rain <- wbClient.fetchPrecipitationStats(location, fromYear, toYear)
    } yield {
      ClimateStats.fromData(location, fromYear, toYear, temp, rain)
    }) map { stats =>
      ClimateQueryResult(List(stats))
    }
  }
}
