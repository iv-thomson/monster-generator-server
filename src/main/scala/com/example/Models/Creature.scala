package com.example.Models

import java.util.UUID

case class Creature(
    name: String,
    image: String,
    vitality: Int,
    strength: Int,
    dexterity: Int,
    id: String = UUID.randomUUID().toString()
)
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
          value
        )
      case None =>
        new Creature(
          creature.name,
          creature.image,
          creature.vitality,
          creature.strength,
          creature.dexterity
        )
    }

}
case class PartialCreature(
    name: String,
    image: String,
    vitality: Int,
    strength: Int,
    dexterity: Int
)
