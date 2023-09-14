package com.example.Models

import java.util.UUID

case class AdventureMap(
    cells: List[AdventureCellDTO],
    name: String,
    id: String = UUID.randomUUID().toString()
) extends Identifiable {
  def addAdventure(adventure: AdventureCellDTO) = {
    new AdventureMap(cells.appended(adventure), name, id)
  }

  def removeAdventure(adventureId: String) = {
    new AdventureMap(
      cells.filter(_.id.get != adventureId),
      name,
      id
    )
  }

  def updateAdventure(adventure: AdventureCellDTO) = {
    new AdventureMap(
      cells.map((cell) =>
        cell.id match {
          case adventure.id => adventure
          case _            => cell
        }
      ),
      name,
      id
    )
  }
}

object AdventureMapFactory {
  def from(
      adventureMap: PartialAdventureMap,
      id: Option[String] = None
  ): AdventureMap =
    id match {
      case Some(value) =>
        new AdventureMap(
          adventureMap.cells,
          adventureMap.name,
          value
        )
      case None =>
        new AdventureMap(
          adventureMap.cells,
          adventureMap.name,
        )
    }

}

case class PartialAdventureMap(
    cells: List[AdventureCellDTO],
    name: String,
)

case class AdventureCellDTO(
    location: Location,
    description: String,
    neighbours: List[String],
    encounterThreshold: Int,
    encounters: List[String],
    id: Option[String] = None
)

case class PartialAdventureCellDTO(
    locationId: String,
    description: String,
    neighbours: List[String],
    encounterThreshold: Int,
    encounters: List[String]
)

class AdventureCell(
    val location: Location,
    description: String,
    val neighbours: List[String],
    private val encounterThreshold: Int,
    val encounters: List[String] = List.empty,
    val id: String = UUID.randomUUID().toString()
) extends Identifiable {
  def addEncounter(encounterId: String) = {
    if (encounters.length < encounterThreshold) {
      new AdventureCell(
        location,
        description,
        neighbours,
        encounterThreshold,
        encounters.appended(encounterId)
      )
    } else {
      this
    }
  }

  def removeEncounter(encounterId: String) = new AdventureCell(
    location,
    description,
    neighbours,
    encounterThreshold,
    encounters.filter(_ != encounterId)
  )

  def clearEncounters() = {
    new AdventureCell(
      location,
      description,
      neighbours,
      encounterThreshold,
      List.empty
    )
  }

  def toDTO() = {
    new AdventureCellDTO(
      location,
      description,
      neighbours,
      encounterThreshold,
      encounters,
      Some(id)
    )
  }

}

object AdventureCell {
  def from(
      adventureCell: AdventureCellDTO
  ): AdventureCell =
    adventureCell.id match {
      case Some(value) =>
        new AdventureCell(
          adventureCell.location,
          adventureCell.description,
          adventureCell.neighbours,
          adventureCell.encounterThreshold: Int,
          adventureCell.encounters: List[String],
          value
        )
      case None =>
        new AdventureCell(
          adventureCell.location,
          adventureCell.description,
          adventureCell.neighbours,
          adventureCell.encounterThreshold: Int,
          adventureCell.encounters: List[String]
        )
    }

}
