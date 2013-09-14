package fr.univnantes.atal.stackexchange

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

import org.apache.logging.log4j.LogManager

object Config {
  private val logger = LogManager.getLogger(getClass().getCanonicalName())

  /*********************** FILES ***********************/

  val baseDir = "/home/mog/work/scala/stackexchange/"
  val dataDir = baseDir + "data/"
  val testCsv = dataDir + "Test.csv"
  val trainCsv = dataDir + "Train.csv"

  /*********************** POSTGRESQL ***********************/

  val server = "localhost"
  val database = "stackexchange"
  val credentials = "/pg/passwd"
  val schema = "public"
  val user = "postgres"
  val password = {
    var password: String = null
    val is = getClass().getResourceAsStream(Config.credentials)
    if (is != null) {
      try {
        val reader = new BufferedReader(new InputStreamReader(is))
        password = reader.readLine
      } catch {
        case ioe: IOException => {
          logger.fatal("Couldn't read the credentials file " +
            "(src/main/resources" + Config.credentials + ". Aborting. " +
            "Message was: " + ioe.getMessage())
          System.exit(1)
        }
      } finally {
        is.close()
      }
    } else {
      logger.fatal("Couldn't find the credentials file " +
        "(src/main/resources" + Config.credentials + ". Aborting.")
      System.exit(1)
    }
    password
  }
  val connectionInfo = "jdbc:postgresql://" + server + "/" + database +
  "?user=" + user + "&password=" + password

}