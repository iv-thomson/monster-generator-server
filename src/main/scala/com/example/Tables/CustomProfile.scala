package com.example.Tables

import com.github.tminglei.slickpg.ExPostgresProfile
import com.github.tminglei.slickpg.PgArraySupport

trait CustomPostgresProfile extends ExPostgresProfile with PgArraySupport {
  
  override val api = CustomPostgresAPI
  
  object CustomPostgresAPI extends API with ArrayImplicits {
    implicit val stringListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}

object CustomPostgresProfile extends CustomPostgresProfile