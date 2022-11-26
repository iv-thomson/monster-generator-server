package com.example.Repositories

import java.io.FileWriter
import java.io.File

import spray.json._

import com.example.Utils.JsonSupport
import com.example.Models.Creature
import java.nio.file.FileSystemException

abstract class Repository[T]() extends JsonSupport {
  val filename: String;

  def write(creature: T) = {
    val allRecords = read()

    val fileWriter = new FileWriter(new File(filename))
    writeAll(allRecords.appended(creature))
  }

  def writeAll(allRecords: List[T]) {
    val fileWriter = new FileWriter(new File(filename))
    fileWriter.write(stringify(allRecords))
    fileWriter.close()
  }

  def read(): List[T] = {
    val lines =
      try readFile()
      catch {
        case e: Throwable => "[]"
      }

    parseJson(lines)
  }

  protected def parseJson(items: String): List[T]
  protected def stringify(items: List[T]): String 


  private def readFile(): String = {
    val source = scala.io.Source.fromFile(filename)
    val lines =
      try source.mkString
      finally source.close()

    lines.toString()
  }
}
