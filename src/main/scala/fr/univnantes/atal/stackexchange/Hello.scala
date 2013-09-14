package fr.univnantes.atal.stackexchange

import java.io.IOException
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.session.Database.threadLocalSession
import org.apache.logging.log4j.LogManager
import fr.univnantes.atal.stackexchange.persistence.TrainTable
import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel
import fr.univnantes.atal.stackexchange.persistence.DatabaseHelper

object Hello {

  private val logger = LogManager.getLogger(getClass.getCanonicalName())

  def main(args: Array[String]) = {
    DatabaseHelper.createTables
    println("Hi!")
    try {
      val is = getClass().getResourceAsStream("/opennlp/models/en-token.bin")
      if (is == null)
        Console println "Nul :'("
      val model = new TokenizerModel(is)
      val tokenizer = new TokenizerME(model)
      val result = tokenizer.tokenize("I'm testing Jenkins.")
      Console println result.mkString(" ")
    } catch {
      case ioe: IOException => throw ioe
      case e: Exception => throw e
    }
  }
}