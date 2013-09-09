package fr.univnantes.atal.stackexchange

import java.io.FileInputStream
import java.io.InputStream
import java.io.IOException
import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel

object Hello {
  def main(args: Array[String]) = {
    println("Hi!")
    try {
      val is = getClass().getResourceAsStream("/opennlp/models/en-token.bin")
      if (is == null)
        Console println "Nul :'("
      val model = new TokenizerModel(is)
      val tokenizer = new TokenizerME(model)
      val result = tokenizer.tokenize("This is the fox, eating the duck.")
      Console println result.mkString(" ")
    } catch {
      case ioe: IOException => throw ioe
      case e: Exception => throw e
    }
  }
}