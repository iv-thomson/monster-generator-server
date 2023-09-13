package com.example.Models

import java.util.UUID

case class Encounter(
    name: String,
    description: String,
    creatures: List[String],
    id: String = UUID.randomUUID().toString()
) extends Identifiable
object EncounterFactory {
  def from(encounter: PartialEncounter, id: Option[String] = None): Encounter =
    id match {
      case Some(value) =>
        new Encounter(
          encounter.name,
          encounter.description,
          encounter.creatures,
          value
        )
      case None =>
        new Encounter(
          encounter.name,
          encounter.description,
          encounter.creatures,
        )
    }

}
case class PartialEncounter(
    name: String,
    description: String,
    creatures: List[String],
)
