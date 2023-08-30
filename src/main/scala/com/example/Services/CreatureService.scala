package com.example.Services

import akka.http.scaladsl.server.Directives

import com.example.Utils.JsonSupport
import com.example.Models.Creature
import com.example.Repositories.CreatureRepository
import com.example.Models.PartialCreature
import com.example.Models.CreatureFactory
import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model

class CreatureService extends Directives with JsonSupport {
  private val repository = new CreatureRepository();
  private val cors = new CORSHandler {}

  val route = cors.corsHandler(
    concat(
      options {
        (complete(model.StatusCodes.OK))
      },
      get {
        concat(
          path("creature") {
            parameters("id".repeated) { (ids) =>
              val creatures =
                if (ids.toList.length > 0) repository.getAll(ids)
                else repository.getAll()

              complete(creatures)
            }
          },
          path("creature" / Remaining) { id =>
            complete(repository.get(id))
          }
        )
      },
      post {
        path("creature") {
          entity(as[PartialCreature]) { creature =>
            repository.create(
              CreatureFactory.from(creature)
            )
            complete("OK")
          }
        }
      },
      delete {
        path("creature" / Remaining) { id =>
          repository.delete(id)
          complete("OK")
        }
      },
      put {
        path("creature" / Remaining) { id =>
          entity(as[PartialCreature]) { creature =>
            repository.update(id, CreatureFactory.from(creature, Option(id)))
            complete("OK")
          }
        }
      }
    )
  )
}
