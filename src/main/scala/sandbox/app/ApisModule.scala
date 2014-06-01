package sandbox.app

import net.codingwell.scalaguice._

import sandbox.frame.spray.ApiPlugin
import sandbox.app.echo.EchoApi
import sandbox.app.climate.ClimateApi

class ApisModule extends ScalaModule
{
  private lazy val apiBinder = ScalaMultibinder.newSetBinder[ApiPlugin](binder)

  protected[this] def bindApi = {
    apiBinder.addBinding
  }

  def configure
  {
    bindApi.to[EchoApi]
    bindApi.to[ClimateApi]
  }
}
