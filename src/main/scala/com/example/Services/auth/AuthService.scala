package com.example.Services.auth

import akka.http.scaladsl.server.Directives._

class AuthService {
  val route = concat(
    new LoginService().route,
    new RegistrationService().route
  )
}
