package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport

import com.example.Models.LocationFactory
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model
import com.example.Repositories.AdventureMapRepository
import com.example.Models.com.example.Models.AdventureMap
import com.example.Models.com.example.Models.PartialAdventureMap
import com.example.Models.com.example.Models.AdventureMapFactory

class AdventureMapService(val endpoint: String) extends Directives with JsonSupport {
  private val repository = new AdventureMapRepository();
  private val cors = new CORSHandler {}

  val route = cors.corsHandler(
    concat(
      options {
        (complete(model.StatusCodes.OK))
      },
      get {
        concat(
          path(endpoint) {
            val adventureMaps = repository.read()
            complete(adventureMaps)
          },
          path(endpoint / Remaining) { id =>
            repository.read().find(_.id == id) match {
              case Some(adventureMap) => complete(adventureMap)
              case None           => complete("Adventure map not found!")
            }
          }
        )
      },
      post {
        path(endpoint) {
          entity(as[PartialAdventureMap]) { adventureMap =>
            repository.write(
              AdventureMapFactory.from(adventureMap)
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
          entity(as[PartialAdventureMap]) { adventureMap =>
            repository.update(id, AdventureMapFactory.from(adventureMap, Some(id)))
            complete("OK")
          }
        }
      }
    )
  )
}
