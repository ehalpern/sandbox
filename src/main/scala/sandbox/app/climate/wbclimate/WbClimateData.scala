package sandbox.app.climate.wbclimate

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sJacksonSupport

/**
 * @see http://data.worldbank.org/developers/climate-data-api
 */
case class WbClimateData(
  gcm: String,
  variable: String,
  fromYear: Int,
  toYear: Int,
  annualData: Option[Seq[Double]]
)
object WbClimateData
{
  trait JsonProtocol extends Json4sJacksonSupport {
    override implicit def json4sJacksonFormats: Formats = DefaultFormats
  }

  def dummyTemperatureData(fromYear: Int, toYear: Int) = {
    WbClimateData("tas", "annualavg", fromYear, toYear, Some(Seq(15.0)))
  }

  def dummyPrecipitationData(fromYear: Int, toYear: Int) = {
    WbClimateData("pr", "annualavg", fromYear, toYear, Some(Seq(100.0)))
  }
}


