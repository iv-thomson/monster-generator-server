package com.example.Tables

import slick.jdbc.PostgresProfile
import com.example.Models.Creature
import com.example.Models.Location
import com.example.Models.Encounter
import com.example.Models.AdventureMap
import com.example.Models.AdventureCellDTO
import play.api.libs.json.JsValue

class SlickTablesGeneric(val profile: PostgresProfile) {
  import CustomPostgresProfile.api._

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

  class EncounterTable(tag: Tag)
      extends Table[Encounter](tag, Some("adventure"), "Encounter") {
    def id = column[String]("encounter_id", O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")
    def creatures = column[List[String]]("creatures")

    override def * = (
      name,
      description,
      creatures,
      id
    ) <> (Encounter.tupled, Encounter.unapply)
  }

  class MapTable(tag: Tag)
      extends Table[AdventureMap](tag, Some("adventure"), "Map") {

    def id = column[String]("map_id", O.PrimaryKey)
    def name = column[String]("name")
    def cells = column[List[AdventureCellDTO]]("cells")

    override def * = (
      cells,
      name,
      id
    ) <> (AdventureMap.tupled, AdventureMap.unapply)
  }

  lazy val creatureTable = TableQuery[CreatureTable]
  lazy val locationTable = TableQuery[LocationTable]
  lazy val encounterTable = TableQuery[EncounterTable]
  lazy val mapTable = TableQuery[MapTable]
}

object SlickTables extends SlickTablesGeneric(PostgresProfile)
