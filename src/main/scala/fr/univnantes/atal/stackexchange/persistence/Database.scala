package fr.univnantes.atal.stackexchange.persistence

import java.sql.{ Connection, DriverManager, ResultSet }
import java.sql.SQLException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException

object Database {
  private val logger = LogManager.getLogger(
    Database.getClass().getCanonicalName())

  private val server = "localhost"
  private val database = "stackexchange"
  private val connectionInfo = "jdbc:postgresql://" + server + "/" + database
  private val credentials = "/pg/passwd"
  private val schema = "public"
  private val user = "postgres"
  private val password = {
    var password: String = null
    val is = getClass().getResourceAsStream(credentials)
    if (is != null) {
      try {
        val reader = new BufferedReader(new InputStreamReader(is))
        password = reader.readLine
      } catch {
        case ioe: IOException => {
          logger.fatal("Couldn't read the credentials file " +
            "(src/main/resources" + credentials + ". Aborting.")
          System.exit(1)
        }
      } finally {
        is.close()
      }
    } else {
      logger.fatal("Couldn't find the credentials file " +
        "(src/main/resources" + credentials + ". Aborting.")
      System.exit(1)
    }
    password
  }

  classOf[org.postgresql.Driver].newInstance

  private var connection: Connection = null

  def connect(): Unit = {
    if (connection == null || !connection.isValid(1)) {
      try {
        connection = DriverManager.getConnection(
          connectionInfo,
          user,
          password)
      } catch {
        case e: SQLException => {
          logger.fatal("Couldn't connect to the database: " + e.getMessage)
          System.exit(1)
        }
      }
    }
  }

  def setup(): Unit = {
    connect
    val query = """
DROP SCHEMA """ + schema + """ CASCADE;
CREATE SCHEMA """ + schema + """;
CREATE TABLE train
(
  id integer NOT NULL,
  title text,
  body text,
  tags text[],
  CONSTRAINT "PK" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE train
  OWNER TO """ + user + """
""";
    val statement = connection.createStatement
    statement.execute(query)
  }

  def close() {
    if (connection != null) {
      connection.close
    }
  }
}