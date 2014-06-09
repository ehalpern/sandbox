package sandbox.app.climate.model

import sandbox.app.climate.wbclimate.WbClimateData

object TemperatureUnit extends Enumeration {
  type TemperatureUnit = Value
  val Celsius, Fahrenheit = Value
}
import TemperatureUnit._

object PrecipitationUnit extends Enumeration {
  type PrecipitationUnit = Value
  val Centimeters, Inches = Value
}
import PrecipitationUnit._

/**
 */
case class ClimateStats(
  location: String,
  fromYear: Int,
  toYear: Int,
  temperature: Option[Temperature] = None,
  precipitation: Option[Precipitation] = None
) {
  require(fromYear >= 1900, "fromYear must be >= 1900")
  require(toYear   >= 1900, "toYear must be >= 1900")
  require(location.matches("[A-Z]{3}"), s"location code ${location} must be 3 letters")
}
object ClimateStats {
  def fromData(
    location: String,
    fromYear: Int, toYear: Int,
    tempData: Seq[WbClimateData],
    precipitationData: Seq[WbClimateData]
  ) = {
    ClimateStats(
      location, fromYear, toYear,
      Temperature.fromData(tempData),
      Precipitation.fromData(precipitationData)
    )
  }
}

case class Temperature(
  unit: TemperatureUnit,
  annual: Double,
  monthly: Option[Seq[Double]] = None
)
object Temperature {
  def fromData(data: Seq[WbClimateData]) = {
    data match {
      case list if list.nonEmpty =>
        Some(Temperature(
          Celsius,
          list.head.annualData match {
            case Some(list) => list.head
            case None => -1.0
          }
        ))
      case _ => None
    }
  }
}

case class Precipitation(
 unit: PrecipitationUnit,
 annual: Double,
 monthly: Option[Seq[Double]] = None
)
object Precipitation {
  def fromData(data: Seq[WbClimateData]) = {
    data match {
      case list if list.nonEmpty =>
        Some(Precipitation(
          Centimeters,
          list.head.annualData match {
            case Some(list) => list.head
            case None => -1.0
          }
        ))
      case _ => None
    }
  }
}
