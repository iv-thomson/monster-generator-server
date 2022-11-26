package com.example.Repositories

import java.io.FileWriter
import java.io.File

import spray.json._

import com.example.Utils.JsonSupport
import com.example.Models.Encounter
import java.nio.file.FileSystemException

class EncounterRepository() extends Repository[Encounter] {
  val filename: String = "output/encounter.json"

  protected def parseJson(items: String): List[Encounter] = {
    val json = items.parseJson

    json.convertTo[List[Encounter]]
  }
  protected def stringify(items: List[Encounter]): String = {
    items.toJson.toString()
  }
}
