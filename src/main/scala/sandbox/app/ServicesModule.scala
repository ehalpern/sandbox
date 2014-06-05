package sandbox.app

import net.codingwell.scalaguice.ScalaModule
import sandbox.app.climate.{ClimateService, ClimateServiceImpl}
import sandbox.app.climate.wbclimate.{WbClimateClientImpl, WbClimateClient}

class ServicesModule extends ScalaModule {
  def configure {
    bind[ClimateService].to[ClimateServiceImpl]
    bind[WbClimateClient].to[WbClimateClientImpl]
  }
}
