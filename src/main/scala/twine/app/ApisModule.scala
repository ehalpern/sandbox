package twine.app

import net.codingwell.scalaguice._
import twine.app.echo.EchoApi
import twine.app.climate.ClimateApi
import twine.rest.RestComponent

class ApisModule extends ScalaModule
{
  private lazy val apiBinder = ScalaMultibinder.newSetBinder[RestComponent](binder)

  protected[this] def bindApi = {
    apiBinder.addBinding
  }

  def configure
  {
    bindApi.to[EchoApi]
    bindApi.to[ClimateApi]
  }
}
