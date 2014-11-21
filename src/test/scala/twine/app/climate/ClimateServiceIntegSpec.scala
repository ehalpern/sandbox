package twine.app.climate

import twine.IntegTestSupport

/**
 * Runs tests against the integrated system.  This includes the tests that are
 * defined in the unit test ClimateServiceSpec.
 */
class ClimateServiceIntegSpec extends ClimateServiceSpec with IntegTestSupport
{
  /**
   * Inject the properly configured climateService.  This overrides the
   * behavior in the unit test that instantiates the service directly with
   * mock dependencies
   */
  override def climateService = getInstance[ClimateService]
}