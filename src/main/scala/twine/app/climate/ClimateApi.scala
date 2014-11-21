package twine.app.climate

import javax.inject.Inject
import org.json4s.ext.EnumNameSerializer
import org.json4s.{Formats, DefaultFormats}
import twine.rest.RestComponent
import scala.concurrent.ExecutionContext
import spray.httpx.Json4sJacksonSupport
import spray.routing.Directives

class ClimateApi @Inject()(climateService: ClimateService)
                          (implicit ec: ExecutionContext)
  extends Directives
  with RestComponent
  with ClimateApiJsonSupport
{
  def route = {
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

  import twine.app.climate.model.{PrecipitationUnit, TemperatureUnit}

  def json4sJacksonFormats: Formats = DefaultFormats +
    new EnumNameSerializer(TemperatureUnit) +
    new EnumNameSerializer(PrecipitationUnit)
}