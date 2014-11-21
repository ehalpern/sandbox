package twine.app.db

import javax.inject.{Singleton, Named, Inject}
import com.googlecode.flyway.core.Flyway

/**
 */
trait DbService {
  def migrate(): Unit
}

@Singleton
class DbServiceImpl @Inject()(
  @Named("db.url") dbUrl: String,
  @Named("db.user") dbUser: String,
  @Named("db.password") dbPassword: String,
  @Named("db.migrateOnStartup") migrateOnStartup: Boolean
) extends DbService
{
  val flyway = {
    val f = new Flyway
    f.setDataSource(dbUrl, dbUser, dbPassword)
    f
  }

  if (migrateOnStartup) {
    migrate
  }

  def migrate()
  {
    flyway.migrate
  }

  // Careful!!!
  def clean()
  {
    flyway.clean
  }
}
