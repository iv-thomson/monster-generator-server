package com.example.Utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.example.Models.Creature
import com.example.Models.PartialCreature
import com.example.Models.Location
import com.example.Models.PartialLocation
import com.example.Models.Encounter
import com.example.Models.PartialEncounter

case class Creatures(values: List[Creature])
case class Locations(values: List[Location])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val printer = PrettyPrinter

  implicit val creatureFormat = jsonFormat6(Creature)
  implicit val partialCreatureFormat = jsonFormat5(PartialCreature)

  implicit val locationFormat = jsonFormat5(Location)
  implicit val partialLocationFormat = jsonFormat4(PartialLocation)

  implicit val encounterFormat = jsonFormat5(Encounter)
  implicit val partialEncounterFormat = jsonFormat4(PartialEncounter)

}
