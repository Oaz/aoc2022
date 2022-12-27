import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal object TestAStar {
  
  @Test
  internal fun `no heuristic`() {
    val result = findWith { 0 }
    assertEquals(24, result.visited)
    assertEquals(listOf('D','M','V','W'), result.path.map { it.label })
  }
  
  @Test
  internal fun `with heuristic`() {
    val result = findWith {
      when (it.label) {
        in 'B'..'M' -> 2*(10-it.distance)
        else -> 0
      }
    }
    assertEquals(9, result.visited)
    assertEquals(listOf('D','M','V','W'), result.path.map { it.label })
  }
  
  private fun findWith(heuristic: (Node) -> Int): AStar.Result<Node> {
    val startAt = Node('A',0)
    val finder = AStar.on<Node>().withNeighborsDistance { n -> n.neighbors }.withHeuristic(heuristic).build()
    return finder.search(startAt) { it.label == 'W' }
  }
  
  data class Node(val label: Char, val distance:Int) {
    val neighbors = when (label) {
      'A' -> next(label,distance)
      in 'B'..'D' -> next('B' + 3 * (label - 'B') + 2, distance)
      in 'E'..'M' -> complete(10)
      in 'N'..'V' -> listOf(Node('W',0) to distance)
      else -> listOf()
    }
    
    private fun complete(total: Int): List<Pair<Node, Int>> {
      return listOf(Node(label + ('N' - 'E'),total-distance) to (total-distance))
    }
    
    fun next(c: Char, base:Int) = listOf(1, 2, 3).map { Node(c + it, base+it) to it }
  }
  
  
}


