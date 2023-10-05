package com.example.Utils

import java.time.Clock

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.time.Instant
import pdi.jwt.{Jwt, JwtAlgorithm}
import play.api.libs.json.{JsValue, Json}
import java.time.Instant
import scala.util.{Failure, Success, Try}
import pdi.jwt.JwtClaim

object JwtUtility {
  val conf = ConfigFactory.load("application.conf");

  private val secretKey = conf.getString("jwt.secretKey")

  def createToken(userId: String): String = {

    val claim = Json.obj(
      "sub" -> userId,
      "exp" -> Instant.now().plusSeconds(3600).getEpochSecond
    )
    Jwt.encode(claim.toString(), secretKey, JwtAlgorithm.HS256)
  }

  def validateToken(token: String): Option[String] = {
    Try(Jwt.decode(token, secretKey, Seq(JwtAlgorithm.HS256))) match {
      case Success(claims) =>
        val claimContent = claims.getOrElse(JwtClaim(""))
        val claimJson: JsValue = Json.parse(claimContent.content)
        val userId = (claimJson \ "sub").asOpt[String]
        userId
      case Failure(_) =>
        None
    }
  }
}
