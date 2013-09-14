package fr.univnantes.atal.stackexchange.persistence

import scala.slick.driver.PostgresDriver.simple._

object TestTable extends Table[(Int, String, String)]("test") {
  def id = column[Int]("id", O.PrimaryKey)
  def title = column[String]("title", O.DBType("text"))
  def body = column[String]("body", O.DBType("text"))
  def * = id ~ title ~ body
}