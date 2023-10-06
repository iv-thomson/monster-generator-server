package com.example.Services.auth
import com.example.Services.RegistrationService

import akka.http.scaladsl.server.Directives._

class AuthService {
  val route = concat(
    new LoginService().route,
    new RegistrationService().route
  )
}
