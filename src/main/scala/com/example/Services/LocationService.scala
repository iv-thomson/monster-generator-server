package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport

import com.example.Models.LocationFactory
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model
import com.example.Models.PartialLocation
import com.example.Repositories.LocationRepository

class LocationService extends Directives with JsonSupport {
  private val repository = new LocationRepository();
  private val cors = new CORSHandler {}

  val route = cors.corsHandler(
    concat(
      options {
        (complete(model.StatusCodes.OK))
      },
      get {
        concat(
          path("location") {
            complete(repository.list)
          },
          path("location" / Remaining) { id =>
            complete(repository.get(id))
          }
        )
      },
      post {
        path("location") {
          entity(as[PartialLocation]) { location =>
            repository.create(LocationFactory.from(location))
            complete("OK")
          }
        }
      },
      delete {
        path("location" / Remaining) { id =>
          repository.delete(id)
          complete("OK")
        }
      },
      put {
        path("location" / Remaining) { id =>
          entity(as[PartialLocation]) { location =>
            repository.update(id, LocationFactory.from(location, Option(id)))

            complete("OK")
          }
        }
      }
    )
  )
}
