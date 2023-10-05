package com.example.Repositories

import com.example.Repositories.DBRepository
import com.example.Tables.Connection
import com.example.Tables.SlickTables

import com.example.Models.user.User
import scala.concurrent.Future
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.util.Success
import scala.util.Failure
import slick.lifted.TableQuery

class UserRepository() {
  import SlickTables.profile.api._
  import com.github.t3hnar.bcrypt._

  implicit val ec =
    ExecutionContext.fromExecutor(Executors.newWorkStealingPool(4))

  type EntityType = User;
  val table = SlickTables.userTable

  def findUserByName(name: String) =
    Connection.db.run(table.filter(_.name === name).result.headOption)

  def validatePassword(user: User, providedPassword: String): Boolean = {
    providedPassword.isBcryptedBounded(user.hash)
  }

  def update(id: String, entity: EntityType): Future[Int] = {
    val updateQuery =
      table.filter(_.id === id).update(entity)

    Connection.db.run(updateQuery)
  }

  def delete(id: String): Future[Int] = {
    val query = table.filter(_.id === id).delete

    Connection.db.run(query)
  }

  def create(entity: EntityType): Future[Int] = {
    val insertQuery = table += entity

    Connection.db.run(insertQuery)
  }

  def get(id: String): Future[EntityType] = {
    list().map(entities => findEntity(entities, id))
  }

  def list(): Future[List[EntityType]] = {
    val query = table.result

    Connection.db.run(query).map(_.toList)
  }

  private def filter(ids: Iterable[String], entities: List[EntityType]) = {
    entities.filter(entity => ids.exists(id => id == entity.id))
  }

  private def findEntity(entities: Seq[EntityType], id: String): EntityType = {
    entities.find(entity => entity.id == id) match {
      case Some(value) => value
      case None        => throw new Exception("User not found")
    }
  }
}
