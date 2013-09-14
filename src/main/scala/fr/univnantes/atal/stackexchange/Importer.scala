package fr.univnantes.atal.stackexchange

import java.io.FileReader
import org.apache.logging.log4j.LogManager
import au.com.bytecode.opencsv.CSVReader
import fr.univnantes.atal.stackexchange.persistence.TrainTable
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.session.Database.threadLocalSession
import fr.univnantes.atal.stackexchange.persistence.TestTable

object Importer {
  private val logger = LogManager.getLogger(getClass().getCanonicalName())

  def main(args: Array[String]): Unit = {
    scala.slick.session.Database.forURL(
      Config.connectionInfo,
      driver = "org.postgresql.Driver") withSession {
        importTrainFile
        importTestFile
      }
  }

  private def readCSV(filePath: String): CSVReader = new CSVReader(
    new FileReader(filePath),
    CSVReader.DEFAULT_SEPARATOR,
    CSVReader.DEFAULT_QUOTE_CHARACTER,
    '\0',
    1)

  def importTrainFile(): Unit = {
    val reader = readCSV(Config.trainCsv)
    var i = 0
    var buffer = Array[(Int, String, String, String)]()
    var line = reader.readNext;
    while (line != null) {
      Console print ("\rHandling element " + i)
      if (line.length == 4) {
        buffer :+= (line(0).toInt, line(1), line(2), line(3))
        if (buffer.length > 5000) {
          TrainTable.insertAll(buffer: _*)
          buffer = Array()
        }
      } else {
        logger.warn("Encountered a line that hasn't 4 fields in the " +
          "training file. It's fishy! Here is the line: " +
          line.mkString(" "))
        reader.close
        return
      }
      line = reader.readNext
      i += 1
    }
    if (!buffer.isEmpty) {
      TrainTable.insertAll(buffer: _*)
    }
    Console println
      reader.close
  }
  
  def importTestFile(): Unit = {
    val reader = readCSV(Config.testCsv)
    var i = 0
    var buffer = Array[(Int, String, String)]()
    var line = reader.readNext;
    while (line != null) {
      Console print ("\rHandling element " + i)
      if (line.length == 3) {
        buffer :+= (line(0).toInt, line(1), line(2))
        if (buffer.length > 5000) {
          TestTable.insertAll(buffer: _*)
          buffer = Array()
        }
      } else {
        logger.warn("Encountered a line that hasn't 4 fields in the " +
          "testing file. It's fishy! Here is the line: " +
          line.mkString(" "))
        reader.close
        return
      }
      line = reader.readNext
      i += 1
    }
    if (!buffer.isEmpty) {
      TestTable.insertAll(buffer: _*)
    }
    Console println
      reader.close
  }
}