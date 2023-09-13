package com.example.Utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.example.Models.Creature
import com.example.Models.PartialCreature
import com.example.Models.Location
import com.example.Models.PartialLocation
import com.example.Models.Encounter
import com.example.Models.PartialEncounter
import com.example.Models.com.example.Models.AdventureCell
import com.example.Models.com.example.Models.AdventureCellDTO
import com.example.Models.com.example.Models.AdventureMap
import com.example.Models.com.example.Models.PartialAdventureCellDTO
import com.example.Models.com.example.Models.PartialAdventureMap

case class Creatures(values: List[Creature])
case class Locations(values: List[Location])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val printer = PrettyPrinter

  implicit val creatureFormat = jsonFormat6(Creature)
  implicit val partialCreatureFormat = jsonFormat5(PartialCreature)

  implicit val locationFormat = jsonFormat4(Location)
  implicit val partialLocationFormat = jsonFormat3(PartialLocation)

  implicit val encounterFormat = jsonFormat4(Encounter)
  implicit val partialEncounterFormat = jsonFormat3(PartialEncounter)

  implicit val adventureCellFormat = jsonFormat6(AdventureCellDTO)
  implicit val partialAdventureCellFormat = jsonFormat5(PartialAdventureCellDTO)
  implicit val adventureMapFormat = jsonFormat2(AdventureMap)
  implicit val partialAdventureMapFormat = jsonFormat1(PartialAdventureMap)
}
