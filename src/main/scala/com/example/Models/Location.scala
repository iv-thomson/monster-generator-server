package com.example.Models

import java.util.UUID

case class Location(
    name: String,
    image: String,
    description: String,
    id: String = UUID.randomUUID().toString()
) extends Identifiable
object LocationFactory {
  def from(location: PartialLocation, id: Option[String] = None): Location =
    id match {
      case Some(value) =>
        new Location(
          location.name,
          location.image,
          location.description,
          value
        )
      case None =>
        new Location(
          location.name,
          location.image,
          location.description
        )
    }

}
case class PartialLocation(
    name: String,
    image: String,
    description: String
)
