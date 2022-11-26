package com.example.Repositories

import java.io.FileWriter
import java.io.File

import spray.json._

import com.example.Utils.JsonSupport
import com.example.Models.Creature
import java.nio.file.FileSystemException

class CreatureRepository extends JsonSupport {
  val filename = "output/creature.json"

  def write(creature: Creature) = {
    val creatures = read()

    val fileWriter = new FileWriter(new File(filename))
    writeAll(creatures.appended(creature))
  }

  def writeAll(creatures: List[Creature]) {
    val fileWriter = new FileWriter(new File(filename))
    fileWriter.write(creatures.toJson.toString())
    fileWriter.close()
  }

  def read(): List[Creature] = {
    val lines =
      try readFile()
      catch {
        case e: Throwable => "[]"
      }

    val json = lines.parseJson

    json.convertTo[List[Creature]]
  }

  def delete(id: String) = {
    val creatures = read()

    writeAll(creatures.filter(_.id != id))
  }

  private def readFile(): String = {
    val source = scala.io.Source.fromFile(filename)
    val lines =
      try source.mkString
      finally source.close()

    lines.toString()
  }
}
