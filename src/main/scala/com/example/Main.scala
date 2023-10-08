package com.example

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.actor.typed.scaladsl.Behaviors
import scala.io.StdIn
import com.example.Models.Creature
import com.example.Services.CreatureService
import com.example.Services.LocationService
import com.example.Services.EncounterService
import com.example.Services.AdventureMapService

import com.example.Services.auth.AuthService
import com.example.Services.AuthHandler

object MonsterGenerator extends App {
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.executionContext

  val port = 8080

  val routes = concat(
    new CreatureService().route,
    new LocationService().route,
    new EncounterService("encounter").route,
    new AdventureMapService("adventure-map").route,
    new AuthService().route
  )

  val bindingFuture =
    Http().newServerAt("localhost", port).bind(routes)

  println(s"listening on port ${port}")

  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
