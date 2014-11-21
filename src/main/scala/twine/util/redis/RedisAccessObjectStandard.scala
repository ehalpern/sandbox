package twine.api

/*
import sandbox.config.SandboxConfig
import com.redis.RedisClient
import com.redis.serialization.{Parse, Format}
import sandbox.util.{GZipper, Json4s}
class RedisAccessObjectStandard[V <: AnyRef](
  prefix: Option[String] = None,
  compress: Boolean = false
) (implicit mf: Manifest[V])
extends RedisAccessObject[V](prefix, compress)
{
  private val client = new RedisClient(RedisHost, RedisPort)

  private implicit val ValueParser: Parse[V] = mf.runtimeClass match {
    case c if classOf[String].isAssignableFrom(c) =>
      Parse.Implicits.parseString.asInstanceOf[Parse[V]]
    case c if classOf[Array[Byte]].isAssignableFrom(c) =>
      Parse.Implicits.parseByteArray.asInstanceOf[Parse[V]]
    case c if classOf[AnyRef].isAssignableFrom(c) =>
      Parse[V] { bytes =>
        val json = if (compress) {
          GZipper.decompress(bytes)
        } else {
          new String(bytes, "UTF-8")
        }
        val o = Json4s.fromJson[V](json)(Json4s.StandardFormats, implicitly)
        o
      }
  }


  private implicit val ValueFormat = {
    mf.runtimeClass match {
      case c if classOf[String].isAssignableFrom(c) =>
        Format.default
      case c if classOf[Array[Byte]].isAssignableFrom(c) =>
        Format.default
      case c if classOf[AnyRef].isAssignableFrom(c) =>
        Format { case o: V =>
          val s = Json4s.toJson(o)(Json4s.StandardFormats)
          if (compress) {
            GZipper.compress(s)
          } else {
            s.getBytes("UTF-8")
          }
        }
    }
  }

  def fetch(keys: Seq[String]): Seq[Option[V]] = {
    val (first, remaining) = addPrefixToKeys(keys).splitAt(1)
    val values = client.mget[V](first(0), remaining:_*)
    values.getOrElse(Nil)
  }

  def store(values: Seq[(String, V)]) = {
    client.mset(addPrefixToPairs(values):_*)
  }
}

*/