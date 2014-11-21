package twine.rest

/**
 * Exception that will translate to an http error response with a specific
 * status code.
 */
case class ExceptionWithStatus(
  statusCode: Int, msg: String, cause: Throwable = null
) extends Exception(msg, cause)
