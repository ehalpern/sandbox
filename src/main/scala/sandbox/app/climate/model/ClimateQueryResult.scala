package sandbox.app.climate.model

/**
 */
case class ClimateQueryResult(
  list: List[ClimateStats]
)
object ClimateQueryResult {
  def dummyResult(location: String, fromYear: Int, toYear: Int) = {
    ClimateQueryResult(List(
      ClimateStats(location, fromYear, toYear,
        Some(Temperature(TemperatureUnit.Celsius, Seq(), 20)),
        Some(Precipitation(PrecipitationUnit.Centimeters, Seq(), 100))
      )
    ))
  }
}