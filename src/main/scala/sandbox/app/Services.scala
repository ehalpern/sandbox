package sandbox.app

import net.codingwell.scalaguice.ScalaModule
import sandbox.app.climate.{ClimateService, ClimateServiceImpl}

class Services extends ScalaModule {
  def configure {
    bind[ClimateService].to[ClimateServiceImpl]
  }
}
