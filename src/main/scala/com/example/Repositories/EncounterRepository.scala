package com.example.Repositories

import com.example.Repositories.DBRepository
import com.example.Tables.Connection
import com.example.Tables.SlickTables
import com.example.Models.Creature
import scala.concurrent.Future
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.util.Success
import scala.util.Failure
import com.example.Models.Encounter

class EncounterRepository() extends DBRepository[Encounter] {
  import SlickTables.profile.api._
  implicit val ec =
    ExecutionContext.fromExecutor(Executors.newWorkStealingPool(4))

  type EntityType = Encounter;
  val table = SlickTables.encounterTable

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

  def list(ids: Iterable[String]) = {
    if (ids.toList.length > 0) getAll(ids)
    else getAll()
  }

  def get(id: String): Future[EntityType] = {
    getAll().map(entities => findEntity(entities, id))
  }

  private def getAll(): Future[List[EntityType]] = {
    val query = table.result

    Connection.db.run(query).map(_.toList)
  }

  private def getAll(ids: Iterable[String]): Future[List[EntityType]] = {
    val query = table.result

    Connection.db.run(query).map(_.toList).map(filter(ids, _))
  }

  private def filter(ids: Iterable[String], entities: List[EntityType]) = {
    entities.filter(entity => ids.exists(id => id == entity.id))
  }

  private def findEntity(entities: Seq[EntityType], id: String): EntityType = {
    entities.find(entity => entity.id == id) match {
      case Some(value) => value
      case None        => throw new Exception("Creature not found")
    }
  }
}
