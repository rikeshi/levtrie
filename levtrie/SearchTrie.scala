/*
 * SearchTrie.scala
 * Extends Trie class with Levenshtein distance search
 * Jan 31, 2017
 */

package trie

import scala.collection.mutable.ArrayBuffer

class SearchTrie extends Trie {

  /*
   * Based on:
   *
   * Fast and Easy Levenshtein distance using a Trie
   * by Steve Hanov
   *
   * http://stevehanov.ca/blog/index.php?id=114
   */
  def search(target: String, maxCost: Int): ArrayBuffer[(String, Int)] = {
    def recursiveSearch(
      node: Node,
      ch: Char,
      word: String,
      prevRow: Array[Int],
      results: ArrayBuffer[(String, Int)],
      maxCost: Int
    ) {
      var curRow = Array.fill[Int](prevRow.length)(prevRow(0) + 1)
      for (i <- 1 to word.length) {
        val insertCost = curRow(i - 1) + 1
        val deleteCost = prevRow(i) + 1
        val replaceCost = if (word.charAt(i - 1) != ch) {
          prevRow(i - 1) + 1
        } else {
          prevRow(i - 1)
        }
        curRow(i) = insertCost.min(deleteCost.min(replaceCost))
      }
      if (curRow.last <= maxCost && node.word != None) {
        results += new Tuple2(node.word.get, curRow.last)
      }
      if (curRow.min <= maxCost) {
        for (edge <- node.edges) {
          recursiveSearch(edge.to, edge.ch, word, curRow, results, maxCost)
        }
      }
    }
    val curRow: Array[Int] = (0 to target.length).toArray
    var results = ArrayBuffer[(String, Int)]()
    for(edge <- this.root.edges) {
      recursiveSearch(edge.to, edge.ch, target, curRow, results, maxCost)
    }
    results
  }

}
