package fr.univnantes.atal.stackexchange

import java.io.FileInputStream
import java.io.InputStream
import java.io.IOException
import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel
import fr.univnantes.atal.stackexchange.persistence.Database

object Hello {
  def main(args: Array[String]) = {
    println("Hi!")
    try {
      val is = getClass().getResourceAsStream("/opennlp/models/en-token.bin")
      if (is == null)
        Console println "Nul :'("
      val model = new TokenizerModel(is)
      val tokenizer = new TokenizerME(model)
      val result = tokenizer.tokenize("I'm testing Jenkins.")
      Console println result.mkString(" ")
      Database.setup
      Database.close
    } catch {
      case ioe: IOException => throw ioe
      case e: Exception => throw e
    }
  }
}