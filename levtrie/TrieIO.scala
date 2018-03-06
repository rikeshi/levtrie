/*
 * TrieIO.scala
 * Jan 31, 2017
 */

package trie

import java.io.File
import scala.io.Source

object TrieIO {

  // read words from file
  // expects processed input with one word on every line
  def readFile(trie: Trie, file: File) {
    if(!file.isFile) {
      println("TrieIO.readFile: $file is not a file.")
      return
    }
    for(line <- Source.fromFile(file).getLines) {
      val word = line.trim
      if (!word.isEmpty)
        trie.insert(word.toLowerCase)
    }
  }

  // read all files in given directory
  def readFiles(trie: Trie, dir: File) {
    if(!dir.isDirectory) {
      println(s"TrieIO.readFiles: $dir is not a directory.")
      return
    }
    for(path <- dir.listFiles) {
      readFile(trie, path)
    }
  }

}

