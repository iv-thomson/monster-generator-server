package com.example.Models

import java.util.UUID

case class Location(
    name: String,
    image: String,
    description: String,
    creatureIds: List[String],
    id: String = UUID.randomUUID().toString()
)
object LocationFactory {
  def from(location: PartialLocation, id: Option[String] = None): Location =
    id match {
      case Some(value) =>
        new Location(
          location.name,
          location.image,
          location.description,
          location.creatureIds,
          value
        )
      case None =>
        new Location(
          location.name,
          location.image,
          location.description,
          location.creatureIds
        )
    }

}
case class PartialLocation(
    name: String,
    image: String,
    description: String,
    creatureIds: List[String]
)
