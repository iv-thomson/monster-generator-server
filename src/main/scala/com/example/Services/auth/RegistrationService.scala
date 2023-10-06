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
import com.example.Models.user.UserRegistrationForm
import com.example.Models.user.UserFactory

class RegistrationService extends Directives with JsonSupport {
  val repo = new UserRepository();

  val route: Route = path("register") {
    post {
      entity(as[UserRegistrationForm]) { form =>
        onComplete(repo.findUserByName(form.name)) {
          case scala.util.Success(Some(user)) =>
            complete(StatusCodes.BadRequest, "Username already exists!")

          case scala.util.Success(None) => {
            if (form.password != form.passwordConfirmation) {
              complete(StatusCodes.Unauthorized, "Passwords do not match!")
            } else {
              repo.create(UserFactory.from(form))
              complete(StatusCodes.OK)
            }
          }

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
