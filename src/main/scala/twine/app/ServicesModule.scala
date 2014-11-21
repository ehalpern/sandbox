package twine.app

import net.codingwell.scalaguice.ScalaModule
import twine.app.climate.{ClimateService, ClimateServiceImpl}
import twine.app.climate.wbclimate.{WbClimateClientImpl, WbClimateClient}
import twine.app.db.{DbServiceImpl, DbService}

class ServicesModule extends ScalaModule {
  def configure {
    bind[ClimateService].to[ClimateServiceImpl]
    bind[WbClimateClient].to[WbClimateClientImpl]
    bind[DbService].to[DbServiceImpl]
  }
}
