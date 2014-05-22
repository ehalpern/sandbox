package sandbox.app.climate

import javax.inject.{Named, Inject}
import akka.actor.ActorRefFactory
import spray.httpx.Json4sJacksonSupport
import org.json4s.{DefaultFormats, Formats}

import spray.http._
import spray.client.pipelining._

import scala.concurrent.Future
import com.typesafe.scalalogging.slf4j.LazyLogging

/**
 * @see http://data.worldbank.org/developers/climate-data-api
 */

object WbClimateJsonProtocol extends Json4sJacksonSupport
{
  override implicit def json4sJacksonFormats: Formats = DefaultFormats

  case class Data(
    gcm: String,
    variable: String,
    fromYear: Int,
    toYear: Int,
    annualData: Option[Seq[Double]],
    monthlyData: Option[Seq[Double]]
  )
}

private[climate] class WbClimateApi @Inject()(
  @Named("climate.api.endpoint") endpoint: String
) (implicit af: ActorRefFactory) extends LazyLogging
{
  import WbClimateJsonProtocol._

  private implicit val ec = af.dispatcher

  private val pipeline: HttpRequest => Future[Seq[Data]] = {
    sendReceive ~>
      unmarshal[Seq[Data]]
  }

  def fetchTemperatureStats(country: String, fromYear: Int, toYear: Int)
  : Future[Seq[Data]] = {
    val uri = apiUri("tas", "annualavg", fromYear, toYear, country)
    pipeline(Get(uri))
  }

  def fetchPrecipitationStats(country: String, fromYear: Int, toYear: Int)
  : Future[Seq[Data]] = {
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
