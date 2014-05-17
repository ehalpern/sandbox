package sandbox.util

import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.Serialization._

/**
  */
object Json4s
{
  /**
   * Formats required for serializing all response objects served through the
   * service layer to json.
   */
  implicit val StandardFormats: Formats = DefaultFormats

  def toJson[A <: AnyRef](instance: A)(implicit formats: Formats) = {
    write(instance)(formats)
  }

  def fromJson[A](json: String)(implicit formats: Formats, mf: Manifest[A]) = {
    read[A](json)(formats, mf)
  }
}
