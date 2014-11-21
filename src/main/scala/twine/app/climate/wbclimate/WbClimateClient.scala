package twine.app.climate.wbclimate

import akka.actor.ActorRefFactory
import javax.inject.{Named, Inject}
import scala.concurrent.Future
import scala.math._
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
    Uri(s)
  }
}

object WbClimateClient {
  // 1920 <= from <= 2080 on 20 year boundary and to must always be from+19
  // from != 2000
  def closestPeriod(from: Int, to: Int) = {
    min(max(from, 1920),2080)/20 * 20 match {
      case x if x == 2000 => (2020, 2039)
      case x => (x, x + 19)
    }
  }
}

