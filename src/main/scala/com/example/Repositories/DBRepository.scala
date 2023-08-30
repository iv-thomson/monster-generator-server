package com.example.Repositories

import scala.concurrent.Future

abstract class DBRepository[T] {
  def update(id: String, creature: T): Future[Int]

  def delete(id: String): Future[Int]

  def create(creature: T): Future[Int]

  def getAll(): Future[List[T]]

  def getAll(ids: Iterable[String]): Future[List[T]]

  def get(id: String): Future[T]
}
