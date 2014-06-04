package sandbox.app.climate.model

/**
 */
case class ClimateQueryResult(
  list: List[ClimateStats]
) {
  list map { cs =>
    require(cs.fromYear >= 1900, "fromYear must be >= 1900")
    require(cs.toYear   >= 1900, "toYear must be >= 1900")
    require(cs.location.matches("[A-Z]{3}"), s"location code ${cs.location} must be 3 letters")
  }
}

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