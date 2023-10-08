package com.example.Services.auth
import com.example.Services.RegistrationService

import akka.http.scaladsl.server.Directives._
import com.example.Services.CORSHandler
import akka.http.scaladsl.model.StatusCodes

class AuthService {
  private val cors = new CORSHandler {}

  val route = cors.corsHandler(
    concat(
      options {
        (complete(StatusCodes.OK))
      },
      new LoginService().route,
      new RegistrationService().route
    )
  )
}
