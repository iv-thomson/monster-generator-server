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

class CreatureRepository() extends DBRepository[Creature] {
  import SlickTables.profile.api._
  implicit val ec =
    ExecutionContext.fromExecutor(Executors.newWorkStealingPool(4))

  def update(id: String, creature: Creature): Future[Int] = {
    val updateQuery =
      SlickTables.creatureTable.filter(_.id === id).update(creature)

    Connection.db.run(updateQuery)
  }

  def delete(id: String): Future[Int] = {
    val query = SlickTables.creatureTable.filter(_.id === id).delete

    Connection.db.run(query)
  }

  def create(creature: Creature): Future[Int] = {
    val insertQuery = SlickTables.creatureTable += creature

    Connection.db.run(insertQuery)
  }

  def getAll(): Future[List[Creature]] = {
    val query = SlickTables.creatureTable.result

    Connection.db.run(query).map(_.toList)
  }

  def getAll(ids: Iterable[String]): Future[List[Creature]] = {
    val query = SlickTables.creatureTable.result

    Connection.db.run(query).map(_.toList).map(filter(ids, _))
  }

  def get(id: String): Future[Creature] = {
    getAll().map(creatures => findCreature(creatures, id))
  }

  private def filter(ids: Iterable[String], creatures: List[Creature]) = {
    creatures.filter(creature => ids.exists(id => id == creature.id))
  }

  private def findCreature(creatures: Seq[Creature], id: String): Creature = {
    creatures.find(creature => creature.id == id) match {
      case Some(value) => value
      case None        => throw new Exception("Creature not found")
    }
  }
}
