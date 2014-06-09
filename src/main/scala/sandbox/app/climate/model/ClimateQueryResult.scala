package sandbox.app.climate.model

object ClimateQueryResult {
  def dummyResult(location: String, fromYear: Int, toYear: Int) = {
    Seq(
      ClimateStats(location, fromYear, toYear,
        Some(Temperature(TemperatureUnit.Celsius, 20)),
        Some(Precipitation(PrecipitationUnit.Centimeters, 100))
      )
    )
  }
}