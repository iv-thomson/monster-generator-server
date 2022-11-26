package com.example.Utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.example.Models.Creature
import com.example.Models.PartialCreature

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val printer = PrettyPrinter
  implicit val creatureFormat = jsonFormat6(Creature)
    implicit val partialCreatureFormat = jsonFormat5(PartialCreature)

}
