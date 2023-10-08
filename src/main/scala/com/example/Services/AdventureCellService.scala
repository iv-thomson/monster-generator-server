package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport

import com.example.Models.LocationFactory
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model
import com.example.Repositories.AdventureRepository
import com.example.Models.AdventureMap
import com.example.Models.PartialAdventureMap
import com.example.Models.AdventureMapFactory

class AdventureMapService(val endpoint: String)
    extends Directives
    with JsonSupport
    with AuthHandler {
  private val repository = new AdventureRepository();
  private val cors = new CORSHandler {}

  val route = cors.corsHandler(
    concat(
      options {
        (complete(model.StatusCodes.OK))
      },
      get {
        concat(
          path(endpoint) {
            authenticateToken() {
              parameters("id".repeated) { (ids) =>
                complete(repository.list(ids))
              }
            }
          },
          path(endpoint / Remaining) { id =>
            authenticateToken() {
              complete(repository.get(id))
            }
          }
        )
      },
      post {
        path(endpoint) {
          authenticateToken() {
            entity(as[PartialAdventureMap]) { adventureMap =>
              repository.create(
                AdventureMapFactory.from(adventureMap)
              )
              complete("OK")
            }
          }
        }
      },
      delete {
        path(endpoint / Remaining) { id =>
          authenticateToken() {
            repository.delete(id)
            complete("OK")
          }
        }
      },
      put {
        path(endpoint / Remaining) { id =>
          authenticateToken() {
            entity(as[PartialAdventureMap]) { adventureMap =>
              repository.update(
                id,
                AdventureMapFactory.from(adventureMap, Some(id))
              )
              complete("OK")
            }
          }
        }
      }
    )
  )
}
