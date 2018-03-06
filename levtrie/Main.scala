/*
 * Main.scala
 * Simple REPL for trying out the Levenshtein search trie.
 * Prints words within edit distance of the target.
 *
 * A suitable input file can be found at:
 * https://github.com/dwyl/english-words/blob/master/words_alpha.txt
 */

import java.io.File
import scala.io.Source
import trie.SearchTrie
import trie.TrieIO

object Main {

  def main(args: Array[String]) {
    if(args.size != 1) {
      println("Usage: scala -cp Main <dir>")
      System.exit(1)
    }
    println("Constructing trie...")
    val trie = new SearchTrie
    TrieIO.readFiles(trie, new File(args(0)))
    println("Ready! You can now make a query, e.g. 'flower 1'.")
    for(ln <- Source.stdin.getLines) {
      val qry = ln.split(" ").map(w => w.toLowerCase).toArray
      if(qry.size == 2) {
        val w = qry(0)
        val d = try { qry(1).toInt } catch { case e: Exception => -1 }
        if(d != -1) {
          println(trie.search(w, d).mkString(" "))
        } else {
          println("Error: '" + qry(1) + "'.toInt failed")
        }
      } else {
        println("Error: Query must be '<word> <max_dist>'")
      }
    }
  }

}
