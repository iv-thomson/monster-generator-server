package com.example.Repositories

import java.io.FileWriter
import java.io.File

import spray.json._

import com.example.Utils.JsonSupport
import com.example.Models.Location
import java.nio.file.FileSystemException

class LocationRepository() extends Repository[Location] {
  val filename: String = "output/location.json"

  protected def parseJson(items: String): List[Location] = {
    val json = items.parseJson

    json.convertTo[List[Location]]
  }
  protected def stringify(items: List[Location]): String = {
    items.toJson.toString()
  }
}
