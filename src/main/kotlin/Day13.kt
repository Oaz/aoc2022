import arrow.core.Either
import java.lang.Exception

class Day13(input: List<String>) {
  
  private val pairs = input.chunked(3).map { readPacket(it[0]) to readPacket(it[1]) }
  
  fun sumOfIndicesOfPairsInRightOrder() = pairs
    .mapIndexed { i, pair -> i + 1 to (pair.first <= pair.second) }
    .sumOf { if (it.second) it.first else 0 }
  
  fun decoderKey(): Int {
    val dividers = listOf("[[2]]", "[[6]]").map { readPacket(it) }
    val packets = (pairs.flatMap { listOf(it.first, it.second) } + dividers).sorted()
    val key1 = packets.indexOf(dividers[0]) + 1
    val key2 = packets.indexOf(dividers[1]) + 1
    return key1 * key2
  }
  
  companion object {
    fun readPacket(input: String) =
      tokenize(input).fold(GroupStack<Either<Int, ComparableTree<Int>>>()) { stack, token ->
        when (token) {
          "[" -> stack.open()
          "]" -> stack.close {ComparableTree.node(it)}
          else -> stack.push(ComparableTree.leaf(token.toInt()))
        }
      }.top().first().fold(
        { throw Exception("Should be a tree") },
        { it }
      )
    
    fun tokenize(input: String) = sequence {
      var intToken = ""
      for (c in input) {
        if (c.isDigit()) {
          intToken += c
          continue
        }
        if (intToken != "") {
          yield(intToken)
          intToken = ""
        }
        if (c != ',')
          yield(c.toString())
      }
    }
  }
}

