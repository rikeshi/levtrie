/*
 * Trie.scala
 * Jan 31, 2017
 */

package trie

import scala.collection.mutable.ArrayBuffer

class Trie {

  var root = new Node

  class Node {

    var word: Option[String] = None
    val edges = new ArrayBuffer[Edge](1)

    // adapted from OpenJDK 8 implementation (java.util.Arrays)
    private def binarySearch(key: Char): Int = {
      var low = 0;
      var high = this.edges.length - 1;
      while (low <= high) {
        val mid = (low + high) >>> 1;
        val midVal = this.edges(mid).ch;
        if (midVal < key)
          low = mid + 1
        else if (midVal > key)
          high = mid - 1
        else
          return mid  // key found
       }
       -(low + 1)     // key not found.
    }

    def getOrAddNode(ch: Char): Node = {
      var i = this.binarySearch(ch)
      if (i < 0) {
        i = -(i + 1)
        this.edges.insert(i, new Edge(ch))
      }
      this.edges(i).to
    }

    def getNode(ch: Char): Option[Node] = {
      var i = this.binarySearch(ch)
      if (i < 0) return None
      Some(this.edges(i).to)
    }

  }

  case class Edge(ch: Char) {
    val to = new Node
  }

  def insert(word: String) {
    var node = this.root
    for (ch <- word)
      node = node.getOrAddNode(ch)
    node.word = Some(word)
  }

  def contains(word: String): Boolean = {
    var node = this.root
    for (ch <- word) {
      node.getNode(ch) match {
        case Some(nextNode) => node = nextNode
        case None => return false
      }
    }
    node.word match {
      case Some(_) => true
      case None => false
    }
  }

}



