package twine.app.climate

import twine.IntegTestSupport

/**
 * Integration test for ClimateApi.
 *
 * This simply extends the ClimateApi unit test and replaces the mocked
 * testRoute with the root application route.  This allows all the
 * tests to run against the integrated system.
 */
class ClimateApiIntegSpec extends ClimateApiSpec
  with IntegTestSupport
{
  override def testRoute = getRestServiceInstance
}