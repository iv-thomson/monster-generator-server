package com.example.Services.auth

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.Models.user.LoginRequest
import akka.http.scaladsl.server.Directives
import com.example.Utils.JsonSupport
import com.example.Utils.JwtUtility

import com.example.Repositories.UserRepository
import scala.util.Success

class LoginService extends Directives with JsonSupport {
  val repo = new UserRepository();

  val route: Route = path("login") {
    post {
      entity(as[LoginRequest]) { loginRequest =>
        // Authenticate the user (e.g., check username and password)

        onComplete(repo.findUserByName(loginRequest.username)) {
          case scala.util.Success(Some(user)) =>
            if (repo.validatePassword(user, loginRequest.password)) {
              val token = JwtUtility.createToken(loginRequest.username)
              complete(StatusCodes.OK, Map("token" -> token))
            } else {
              complete(StatusCodes.Unauthorized, "Invalid username or password")
            }

          case scala.util.Success(None) =>
            complete(StatusCodes.Unauthorized, "Invalid username or password")

          case scala.util.Failure(ex) =>
            complete(
              StatusCodes.InternalServerError,
              s"An error occurred: ${ex.getMessage}"
            )

        }

      }
    }
  }

}
