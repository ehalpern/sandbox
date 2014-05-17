package sandbox.util

import spray.httpx.encoding.{GzipDecompressor, GzipCompressor}

/**
  */
object GZipper
{
  private def compressor = new GzipCompressor
  private def decompressor = new GzipDecompressor

  def compress(uncompressed: String): Array[Byte] = {
    compressor.compress(uncompressed.getBytes("UTF-8")).finish()
  }

  def decompress(compressed: Array[Byte]): String = {
    new String(decompressor.decompress(compressed), "UTF-8")
  }
}
