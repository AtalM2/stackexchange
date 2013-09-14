package fr.univnantes.atal.stackexchange.persistence

import scala.slick.driver.PostgresDriver.simple._

object TrainTable extends Table[(Int, String, String, String)]("train") {
  def id = column[Int]("id", O.PrimaryKey)
  def title = column[String]("title", O.DBType("text"))
  def body = column[String]("body", O.DBType("text"))
  def tags = column[String]("tags", O.DBType("text"))
  def * = id ~ title ~ body ~ tags
}