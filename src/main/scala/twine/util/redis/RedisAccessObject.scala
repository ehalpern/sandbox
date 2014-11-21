package twine.util.redis

abstract class RedisAccessObject[V <: AnyRef](
  val prefix: Option[String] = None,
  val compress: Boolean = false
) (implicit mf: Manifest[V])
{
  def fetch(keys: Seq[String]): Map[String, V]

  def store(values: Seq[(String, V)])

  protected def addPrefixToKeys(keys: Seq[String]): Seq[String] = {
    prefix match {
      case Some(p) => keys map { key => addPrefix(key) }
      case None => keys
    }
  }

  protected def addPrefixToPairs[A](entries: Seq[(String, A)]): Seq[(String, A)] = {
    prefix match {
      case Some(p) => entries map {
        case (k, v) => (addPrefix(k), v)
      }
      case None => entries
    }
  }

  protected def addPrefix(key: String): String = {
    prefix match {
      case Some(p) => s"${p}${key}"
      case None => key
    }
  }
}