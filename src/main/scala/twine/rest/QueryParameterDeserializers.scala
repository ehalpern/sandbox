package twine.rest

import spray.httpx.unmarshalling.{Deserializer, MalformedContent}

/**
 */
object QueryParameterDeserializers
{
  // @todo replace both with generic Deserializer[String, Seq[A]]

  implicit val String2StringSeqConverter = new Deserializer[String, Seq[String]] {
    def apply(value: String) = {
      val array = value.trim.split("""\s*,\s*""")
      Right(array.toSeq)
    }
  }

  implicit val String2IntSeqConverter = new Deserializer[String, Seq[Int]] {
    def apply(value: String) = {
      val array = value.trim.split("""\s*,\s*""")
      try {
        Right(array.map(Integer.parseInt(_)).toSeq)
      } catch {
        case e: NumberFormatException =>
          Left(MalformedContent(s"'$value' is not a comma separated list of Ints"))
      }
    }
  }
}
