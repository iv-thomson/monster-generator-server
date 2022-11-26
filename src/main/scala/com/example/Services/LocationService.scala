package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport

import com.example.Models.LocationFactory
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model
import _root_.com.example.Models.PartialLocation
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
            val creatures = repository.read()
            complete(creatures)
          },
          path("location" / Remaining) { id =>
            repository.read().find(_.id == id) match {
              case Some(location) => complete(location)
              case None           => complete("Creature not found!")
            }
          }
        )
      },
      post {
        path("location") {
          entity(as[PartialLocation]) { location =>
            repository.write(
              LocationFactory.from(location)
            )
            complete("OK")
          }
        }
      },
      delete {
        path("location" / Remaining) { id =>
          repository.delete(id)
          complete("OK")
        }
      }
    )
  )
}
