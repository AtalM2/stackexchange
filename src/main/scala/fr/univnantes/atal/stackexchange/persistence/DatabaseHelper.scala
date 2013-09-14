package fr.univnantes.atal.stackexchange.persistence

import scala.slick.driver.PostgresDriver.simple.ddlToDDLInvoker
import scala.slick.session.Database.threadLocalSession
import scala.slick.session.Database
import scala.slick.jdbc.{ StaticQuery => Q }
import org.apache.logging.log4j.LogManager

import fr.univnantes.atal.stackexchange.Config

object DatabaseHelper {
  private val logger = LogManager.getLogger(getClass().getCanonicalName())

  def createTables(): Unit = {
    scala.slick.session.Database.forURL(
      Config.connectionInfo,
      driver = "org.postgresql.Driver") withSession {
        Q.updateNA("drop schema public cascade; create schema public;").execute
        (TrainTable.ddl ++ TestTable.ddl).create
      }
  }
}