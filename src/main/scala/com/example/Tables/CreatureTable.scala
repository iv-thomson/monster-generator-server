package com.example.Tables

import slick.jdbc.PostgresProfile
import com.example.Models.Creature

class SlickTablesGeneric(val profile: PostgresProfile) {
  import profile.api._

  class CreatureTable(tag: Tag)
      extends Table[Creature](tag, Some("adventure"), "Creature") {
    def id = column[String]("creature_id", O.PrimaryKey, O.AutoInc)
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

  lazy val creatureTable = TableQuery[CreatureTable]
}

object SlickTables extends SlickTablesGeneric(PostgresProfile)
