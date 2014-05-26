package sandbox.app.climate

import spray.routing.Directives

import javax.inject.Inject
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.concurrent.ExecutionContext
import sandbox.server.ApiPlugin
import spray.httpx.Json4sJacksonSupport
import org.json4s.{Formats, DefaultFormats}
import org.json4s.ext.EnumNameSerializer

class ClimateApi @Inject()(
  climateService: ClimateService
) extends Directives
  with ApiPlugin
  with LazyLogging
  with ClimateApiJsonSupport
{
  def route(implicit ec: ExecutionContext) = {
    path("climate") {
      get {
        parameters('location, 'fromYear.as[Int], 'toYear.as[Int]) { (
          location: String,
          fromYear: Int,
          toYear: Int
        ) =>
          complete {
            climateService.query(location, fromYear, toYear)
          }
        }
      }
    }
  }
}

trait ClimateApiJsonSupport extends Json4sJacksonSupport {
  import sandbox.app.climate.model.{PrecipitationUnit, TemperatureUnit}
  def json4sJacksonFormats: Formats = DefaultFormats +
    new EnumNameSerializer(TemperatureUnit) +
    new EnumNameSerializer(PrecipitationUnit)
}