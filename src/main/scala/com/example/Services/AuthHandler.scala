package com.example.Services

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directive
import com.example.Utils.JwtUtility
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.AuthorizationFailedRejection
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.Authorization
import akka.http.scaladsl.model.headers.OAuth2BearerToken
import pdi.jwt.Jwt
import akka.http.scaladsl.model.HttpHeader

trait AuthHandler {
  def extractBearerToken: Directive1[Option[String]] =
    optionalHeaderValueByName("Authorization").map {
      case Some(header) if header.startsWith("Bearer ") =>
        val token = header.stripPrefix("Bearer ").trim
        Some(token)
      case _ =>
        None
    }

  def authenticateToken(): Directive[Unit] = {
    extractBearerToken.flatMap {
      case Some(token) =>
        JwtUtility.validateToken(token) match {
          case Some(value) => pass
          case None => complete(StatusCodes.Unauthorized, "Invalid token")
        }

      case None =>
        complete(StatusCodes.Unauthorized, "Token missing")
    }
  }

}
