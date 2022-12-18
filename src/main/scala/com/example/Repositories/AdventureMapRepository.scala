package com.example.Repositories

import java.io.FileWriter
import java.io.File

import spray.json._

import com.example.Utils.JsonSupport
import com.example.Models.Location
import java.nio.file.FileSystemException
import com.example.Models.com.example.Models.AdventureMap

class AdventureMapRepository() extends Repository[AdventureMap] {
  val filename: String = "output/adventureMap.json"

  protected def parseJson(items: String): List[AdventureMap] = {
    val json = items.parseJson

    json.convertTo[List[AdventureMap]]
  }
  protected def stringify(items: List[AdventureMap]): String = {
    items.toJson.toString()
  }
}
