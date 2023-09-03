package com.example.Tables

import slick.jdbc.PostgresProfile
import com.example.Models.Creature
import com.example.Models.Location

class SlickTablesGeneric(val profile: PostgresProfile) {
  import profile.api._

  class CreatureTable(tag: Tag)
      extends Table[Creature](tag, Some("adventure"), "Creature") {
    def id = column[String]("creature_id", O.PrimaryKey)
    def name = column[String]("name")
    def image = column[String]("image")
    def vitality = column[Int]("vitality")
    def strength = column[Int]("strength")
    def dexterity = column[Int]("dexterity")

    override def * = (
      name,
      image,
      vitality,
      strength,
      dexterity,
      id
    ) <> (Creature.tupled, Creature.unapply)
  }

  class LocationTable(tag: Tag)
      extends Table[Location](tag, Some("adventure"), "Location") {
    def id = column[String]("location_id", O.PrimaryKey)
    def name = column[String]("name")
    def image = column[String]("image")
    def description = column[String]("description")

    override def * = (
      name,
      image,
      description,
      id
    ) <> (Location.tupled, Location.unapply)
  }

  lazy val creatureTable = TableQuery[CreatureTable]
  lazy val locationTable = TableQuery[LocationTable]
}

object SlickTables extends SlickTablesGeneric(PostgresProfile)
