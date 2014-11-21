package twine.app.climate.model

object ClimateQueryResult {
  def dummyResult(location: String, fromYear: Int, toYear: Int) = {
    ClimateStats(location, fromYear, toYear,
      Temperature(TemperatureUnit.Celsius, 20),
      Precipitation(PrecipitationUnit.Centimeters, 100)
    )
  }
}