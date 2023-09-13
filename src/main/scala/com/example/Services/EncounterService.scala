package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport

import com.example.Models.PartialEncounter
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model
import com.example.Repositories.EncounterRepository
import com.example.Models.EncounterFactory
import scala.util.Success
import scala.util.Failure

class EncounterService(val endpoint: String)
    extends Directives
    with JsonSupport {
  private val repository = new EncounterRepository();
  private val cors = new CORSHandler {}
  implicit val ec: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.global

  val route = cors.corsHandler(
    concat(
      options {
        (complete(model.StatusCodes.OK))
      },
      get {
        concat(
          path(endpoint) {
            parameters("id".repeated) { (ids) =>
              complete(repository.list(ids))
            }
          },
          path(endpoint / Remaining) { id =>
            complete(repository.get(id))
          }
        )
      },
      post {
        path(endpoint) {
          entity(as[PartialEncounter]) { encounter =>
            repository.create(
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
