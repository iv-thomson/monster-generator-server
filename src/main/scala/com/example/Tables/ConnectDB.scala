package com.example.Tables
import slick.jdbc.PostgresProfile.api._


object Connection {
  val db = Database.forConfig("postgres")
}