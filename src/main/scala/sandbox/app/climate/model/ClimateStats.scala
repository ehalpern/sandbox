package sandbox.app.climate.model

import sandbox.app.climate.WbClimateClient.Data

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
}
object ClimateStats {
  def fromData(
    location: String,
    fromYear: Int, toYear: Int,
    tempData: Seq[Data],
    precipitationData: Seq[Data]
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
  monthly: Seq[Double],
  annual: Double
)
object Temperature {
  def fromData(data: Seq[Data]) = {
    data match {
      case list if list.nonEmpty =>
        Some(Temperature(
          Celsius,
          Seq(),
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
 monthly: Seq[Double],
 annual: Double
)
object Precipitation {
  def fromData(data: Seq[Data]) = {
    data match {
      case list if list.nonEmpty =>
        Some(Precipitation(
          Centimeters,
          Seq(),
          list.head.annualData match {
            case Some(list) => list.head
            case None => -1.0
          }
        ))
      case _ => None
    }
  }
}
