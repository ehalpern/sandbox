package twine.app.user

import javax.inject.Inject
import twine.app.db.DbService

/**
 */
trait UserService {
  def authenticate(name: String, password: String)
}

class UserServiceImpl @Inject()(
  db: DbService
) extends UserService
{
  def create(name: String, password: String) = {

  }

  def authenticate(name: String, password: String) = {

  }

}

