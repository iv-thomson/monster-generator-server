package com.example.Tables

import com.github.tminglei.slickpg._
import play.api.libs.json.JsValue
import play.api.libs.json.Json

import com.example.Utils.JsonSupport
import com.example.Models.AdventureMap
import com.example.Models.AdventureCell
import com.example.Models.AdventureCellDTO

trait CustomPostgresProfile
    extends ExPostgresProfile
    with PgJsonSupport
    with PgArraySupport {
  override def pgjson = "jsonb"
  override protected def computeCapabilities: Set[slick.basic.Capability] =
    super.computeCapabilities + slick.jdbc.JdbcCapabilities.insertOrUpdate

  override val api = CustomPGAPI
  import spray.json._
  object CustomPGAPI extends API with ArrayImplicits with JsonSupport {
    implicit val strListTypeMapper =
      new SimpleArrayJdbcType[String]("text").to(_.toList)

    implicit def mapper: BaseColumnType[List[AdventureCellDTO]] =
      MappedColumnType.base[List[AdventureCellDTO], String](
        v => v.toJson.toString,
        s => s.parseJson.convertTo[List[AdventureCellDTO]]
      )
  }
}

object CustomPostgresProfile extends CustomPostgresProfile
