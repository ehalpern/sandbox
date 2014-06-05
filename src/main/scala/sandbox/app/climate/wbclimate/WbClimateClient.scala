package sandbox.app.climate.wbclimate

import akka.actor.ActorRefFactory
import com.typesafe.scalalogging.slf4j.LazyLogging
import javax.inject.{Named, Inject}
import scala.concurrent.Future
import spray.client.pipelining._
import spray.http._

/**
 * @see http://data.worldbank.org/developers/climate-data-api
 */
trait WbClimateClient {
  def fetchTemperatureStats(country: String, fromYear: Int, toYear: Int)
  : Future[Seq[WbClimateData]]

  def fetchPrecipitationStats(country: String, fromYear: Int, toYear: Int)
  : Future[Seq[WbClimateData]]
}

class WbClimateClientImpl @Inject()(@Named("wbclimate.endpoint") endpoint: String)
                                   (implicit af: ActorRefFactory)
  extends WbClimateClient
  with WbClimateData.JsonProtocol
  with LazyLogging
{
  private implicit val ec = af.dispatcher

  private val pipeline: HttpRequest => Future[Seq[WbClimateData]] = {
    sendReceive ~>
      unmarshal[Seq[WbClimateData]]
  }

  def fetchTemperatureStats(country: String, fromYear: Int, toYear: Int)
  : Future[Seq[WbClimateData]] = {
    val uri = apiUri("tas", "annualavg", fromYear, toYear, country)
    pipeline(Get(uri))
  }

  def fetchPrecipitationStats(country: String, fromYear: Int, toYear: Int)
  : Future[Seq[WbClimateData]] = {
    val uri = apiUri("pr", "annualavg", fromYear, toYear, country)
    pipeline(Get(uri))
  }

  private def apiUri(
    dataType: String, dataStat: String,
    fromYear: Int, toYear: Int,
    country: String
  ) = {
    val s = s"$endpoint/country/$dataStat/$dataType/$fromYear/$toYear/$country.json"
    logger.debug(s"URI: $s")
    Uri(s)
  }
}
