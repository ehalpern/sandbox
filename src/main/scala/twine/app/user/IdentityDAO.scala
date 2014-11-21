package twine.app.user

import java.sql.Timestamp

import org.joda.time.DateTime
import java.util.UUID
import scala.slick.driver.JdbcProfile


/**
 */
class IdentityDAO(val driver: JdbcProfile)
{

  import driver.simple._

  case class User(id: String, name: String, timestamp: Timestamp)

  class UsersTable(tag: Tag) extends Table[User](tag, "Users")
  {
    def id = column[String]("id", O.PrimaryKey)
    def name = column[String]("name")
    def created = column[Timestamp]("created")
    def * = (id, name, created) <> (User.tupled, User.unapply)
  }
  val users = TableQuery[UsersTable]

  /**
   * Inserts a new user
   */
  def insert(name: String)(implicit session: Session): String = {
    val id = UUID.randomUUID.toString
    users.insert(User(id, name, new Timestamp(System.currentTimeMillis())))
    id
  }

  /**
   * Gets a user by id
   **/
  def get(id: String)(implicit session: Session): Option[User] = {
    users.withFilter(_.id === id).firstOption
  }

  /**
   * Finds a user by name
   */
  def findByName(name: String)(implicit s: Session): Option[User] = {
    users.withFilter(_.name === name).firstOption
  }
}