package twine.util.redis
/*
import com.redis.RedisClient
import com.redis.serialization.{DefaultFormats, Format}
import sandbox.util.{GZipper, Json4s}
import akka.actor.ActorSystem
import akka.util.Timeout
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.Await

class RedisAccessObjectNb[V <: AnyRef](
  prefix: Option[String] = None,
  compress: Boolean = false
) (implicit mf: Manifest[V])
extends RedisAccessObject[V](prefix, compress)
{
  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(RedisTimeout)
  private val awaitTimeout = Duration(RedisTimeout, TimeUnit.MILLISECONDS)
  private val client = RedisClient(RedisHost, RedisPort)

  implicit val ValueFormat = mf.runtimeClass match {
    case c if classOf[String].isAssignableFrom(c) =>
      DefaultFormats.stringFormat.asInstanceOf[Format[V]]
    case c if classOf[Array[Byte]].isAssignableFrom(c) =>
      (new Format[Array[Byte]] {
        def read(bytes: String): Array[Byte] = {
          bytes.getBytes("ISO-8859-1")
        }

        def write(value: Array[Byte]) = {
          new String(value, "ISO-8859-1")
        }
      }).asInstanceOf[Format[V]]
    case c if classOf[AnyRef].isAssignableFrom(c) =>
      new Format[V] {
        def read(value: String): V = {
          val json = if (compress) {
            GZipper.decompress(value.getBytes("ISO-8859-1"))
          } else {
            value
          }
          Json4s.fromJson[V](json)(Json4s.StandardFormats, implicitly)
        }

        def write(value: V) = {
          val json = Json4s.toJson(value)(Json4s.StandardFormats)
          if (compress) {
            new String(GZipper.compress(json), "ISO-8859-1")
          } else {
            json
          }
        }
      }
    case _ => throw new AssertionError("unhandled type")
  }


  def fetch(keys: Seq[String]): Map[String, V] = {
    val (first, remaining) = addPrefixToKeys(keys).splitAt(1)
    val future = client.mget[V](first(0), remaining:_*)
    val result = Await.result(future, awaitTimeout)
    result
  }

  def store(values: Seq[(String, V)]) {
    val future = client.mset(addPrefixToPairs(values):_*)
    Await.ready(future, awaitTimeout)
  }
}

*/