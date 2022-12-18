package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport

import com.example.Models.PartialEncounter
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model
import com.example.Repositories.EncounterRepository
import com.example.Models.EncounterFactory

class EncounterService(val endpoint: String)
    extends Directives
    with JsonSupport {
  private val repository = new EncounterRepository();
  private val cors = new CORSHandler {}

  val route = cors.corsHandler(
    concat(
      options {
        (complete(model.StatusCodes.OK))
      },
      get {
        concat(
          path(endpoint) {
            parameters("id".repeated) { (ids) =>
              val encounters = repository.read()
              val filteredEncounters =
                ids.flatMap((id) => encounters.find(_.id == id))

              if (filteredEncounters.toList.length > 0) {
                complete(filteredEncounters)
              } else {
                complete(encounters)
              }
            }
          },
          path(endpoint / Remaining) { id =>
            repository.read().find(_.id == id) match {
              case Some(encounter) => complete(encounter)
              case None           => complete("Encounter not found!")
            }
          }
        )
      },
      post {
        path(endpoint) {
          entity(as[PartialEncounter]) { encounter =>
            repository.write(
              EncounterFactory.from(encounter)
            )
            complete("OK")
          }
        }
      },
      delete {
        path(endpoint / Remaining) { id =>
          repository.delete(id)
          complete("OK")
        }
      },
      put {
        path(endpoint / Remaining) { id =>
          entity(as[PartialEncounter]) { encounter =>
            repository.update(id, EncounterFactory.from(encounter, Option(id)))
            complete("OK")
          }
        }
      }
    )
  )
}
