package com.example.Utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.example.Models.Creature
import com.example.Models.PartialCreature
import com.example.Models.Location
import com.example.Models.PartialLocation
import com.example.Models.Encounter
import com.example.Models.PartialEncounter
import com.example.Models.AdventureCell
import com.example.Models.AdventureCellDTO
import com.example.Models.AdventureMap
import com.example.Models.PartialAdventureCellDTO
import com.example.Models.PartialAdventureMap
import com.example.Models.user.LoginRequest

case class Creatures(values: List[Creature])
case class Locations(values: List[Location])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val printer = PrettyPrinter

  implicit val creatureFormat = jsonFormat7(Creature)
  implicit val partialCreatureFormat = jsonFormat6(PartialCreature)

  implicit val locationFormat = jsonFormat4(Location)
  implicit val partialLocationFormat = jsonFormat3(PartialLocation)

  implicit val encounterFormat = jsonFormat4(Encounter)
  implicit val partialEncounterFormat = jsonFormat3(PartialEncounter)

  implicit val adventureCellFormat = jsonFormat6(AdventureCellDTO)
  implicit val partialAdventureCellFormat = jsonFormat5(PartialAdventureCellDTO)
  implicit val adventureMapFormat = jsonFormat3(AdventureMap)
  implicit val partialAdventureMapFormat = jsonFormat2(PartialAdventureMap)

  implicit val loginRequestFormat = jsonFormat2(LoginRequest)
}
