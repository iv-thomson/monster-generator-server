package com.example.Models

import java.util.UUID

case class Creature(
    name: String,
    image: String,
    vitality: Int,
    strength: Int,
    dexterity: Int,
    tags: List[String] = List.empty,
    id: String = UUID.randomUUID().toString()
) extends Identifiable
object CreatureFactory {
  def from(creature: PartialCreature, id: Option[String] = None): Creature =
    id match {
      case Some(value) =>
        new Creature(
          creature.name,
          creature.image,
          creature.vitality,
          creature.strength,
          creature.dexterity,
          creature.tags,
          value
        )
      case None =>
        new Creature(
          creature.name,
          creature.image,
          creature.vitality,
          creature.strength,
          creature.dexterity,
          creature.tags,
        )
    }

}
case class PartialCreature(
    name: String,
    image: String,
    vitality: Int,
    strength: Int,
    dexterity: Int,
    tags: List[String]
)
