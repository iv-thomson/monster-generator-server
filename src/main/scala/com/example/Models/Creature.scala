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
  def from(creature: PartialCreature): Creature = {
    new Creature(
      creature.name,
      creature.image,
      creature.vitality,
      creature.dexterity,
      creature.strength
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
