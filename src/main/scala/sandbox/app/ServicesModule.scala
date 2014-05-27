package sandbox.app

import net.codingwell.scalaguice.ScalaModule
import sandbox.app.climate.{ClimateService, ClimateServiceImpl}

class ServicesModule extends ScalaModule {
  def configure {
    bind[ClimateService].to[ClimateServiceImpl]
  }
}
