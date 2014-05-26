package sandbox.util.redis

import org.scalatest.FunSpec
import sandbox.util.GZipper

/**
 */
case class TestCaseClass(intField: Int, stringField: String, boolField: Boolean)

class RedisAccessObjectSpec extends FunSpec {

  describe ("A RedisDataObject[String]") {
    val orig = Seq(("1" -> "one"), ("2" -> "two"), ("3" -> "three"))
    val (keys, values) = orig.unzip

    it ("Should fetch what it stores") {
      /*
      val rdo = new RedisAccessObjectNb[String]
      rdo.store(orig)
      assertResult(values) {
        rdo.fetch(keys).values.toList
      }
      */
    }
  }

  describe ("A RedisDataObject[Array[Byte]]") {
    val orig = Seq(("1" -> "one".getBytes("UTF-8")))
    val (keys, values) = orig.unzip

    it ("Should fetch what it stores") {
      /*
      val rdo = new RedisAccessObjectNb[Array[Byte]]
      rdo.store(orig)
      assertResult(values.toArray.deep) {
        rdo.fetch(keys).values.toArray.deep
      }
      */
    }
  }

  describe ("A RedisDataObject[TestCaseClass]")  {
    val orig = Seq(1, 2, 3) map { i =>
      (i.toString, TestCaseClass(i, i.toString, i % 2 == 0))
    }
    val (keys, values) = orig.unzip

    it ("Should fetch what it stores") {
      /*
      val rdo = new RedisAccessObjectNb[TestCaseClass]
      rdo.store(orig)
      assertResult(values) {
        rdo.fetch(keys).values.toList
      }
      */
    }

    it ("Should work with compression enabled") {
      /*
      val rdoWithCompression = new RedisAccessObjectNb[TestCaseClass](compress = true)
      rdoWithCompression.store(orig)
      assertResult(values, "retrieved values should be same as values stored") {
        rdoWithCompression.fetch(keys).values.toList
      }
      val rdoRaw = new RedisAccessObjectNb[Array[Byte]]
      val rawResults = rdoRaw.fetch(keys).values.toSeq
      assert(rawResults.size == values.size,
        "rdoRaw.fetch should return all entries")

      assertResult(values(0), "raw value should be in gzip format") {
        import Json4s.StandardFormats
        val json = GZipper.decompress(rawResults(0))
        val o = Json4s.fromJson[TestCaseClass](json)
        o
      }
      */
    }

    describe ("A RedisDataObject with a key prefix") {
      /*
      val orig = Seq(("1" -> "one"), ("2" -> "two"), ("3" -> "three"))
      val (keys, values) = orig.unzip
      val rdo = new RedisAccessObjectNb[String](prefix = Some("pfx"))

      it ("Should fetch what it stores") {
        rdo.store(orig)
        assertResult(values) {
          rdo.fetch(keys).values.toList
        }
      }

      it ("Should add the prefix to each key") {
        val rdoNoPrefix = new RedisAccessObjectNb[String]
        val testKey = keys(0)
        assertResult(values(0), "Retrieve value by prefixed key") {
          val result = rdoNoPrefix.fetch(Seq(s"pfx${testKey}")).values.toSeq
          result(0)
        }
      }
      */
    }
  }
}
