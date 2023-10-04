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

class CreatureRepository() {
  import SlickTables.profile.api._
  implicit val ec =
    ExecutionContext.fromExecutor(Executors.newWorkStealingPool(4))

  type EntityType = Creature;
  val table = SlickTables.creatureTable

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

  def list(ids: Iterable[String], tags: Iterable[String]) = {
    val idList = ids.toList
    val tagList = tags.toList

    if (idList.length > 0 && idList.length > 0) getAll(ids, tags)
    else if (ids.toList.length > 0) getAll(ids)
    else if (tagList.toList.length > 0) getAllByTags(tags)
    else getAll()
  }

  def getAllByTags(tags: Iterable[String]) = {
    val query = table.result.map(
      _.filter((c) => c.tags.containsSlice(tags.toSeq))
    )

    Connection.db.run(query).map(_.toList)
  }

  def getAll(ids: Iterable[String], tags: Iterable[String]) = {
    val query = table.result.map(
      _.filter((c) =>
        c.tags.containsSlice(tags.toSeq) && ids.toList.contains(
          c.id
        )
      )
    )

    Connection.db.run(query).map(_.toList)
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
