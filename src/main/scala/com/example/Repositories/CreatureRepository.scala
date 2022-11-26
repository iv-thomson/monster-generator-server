package com.example.Repositories

import java.io.FileWriter
import java.io.File

import spray.json._

import com.example.Utils.JsonSupport
import com.example.Models.Creature
import java.nio.file.FileSystemException

class CreatureRepository() extends Repository[Creature] {
  val filename: String = "output/creature.json"

  protected def parseJson(items: String): List[Creature] = {
    val json = items.parseJson

    json.convertTo[List[Creature]]
  }
  protected def stringify(items: List[Creature]): String = {
    items.toJson.toString()
  }

  def delete(id: String) = {
    val allRecords = read()

    writeAll(allRecords.filter(_.id != id))
  }

  def update(id: String, item: Creature) = {
    val allRecords = read()

    writeAll(allRecords.map((record) => {
      record.id match {
        case `id` => item
        case _  => record
      }
    }))
  }
}
