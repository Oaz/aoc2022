import java.util.PriorityQueue

class AStar<NODE> private constructor(
  val neighbors: (NODE) -> List<Pair<NODE,Int>>,
  val heuristic: (NODE) -> Int,
  val newOpenSet: ((NODE) -> Int) -> OpenSet<NODE>
)
{
  companion object {
    fun <NODE> on(nodes:List<NODE>) = init(Builder<NODE>())
    fun <NODE> on(node:NODE) = init(Builder<NODE>())
    fun <NODE> on() = init(Builder<NODE>())
    private fun <NODE> init(builder:Builder<NODE>) = builder.usingPriorityQueueAndSet()
  }
  
  data class Builder<NODE> internal constructor(
    var newOpenSet: (((NODE) -> Int) -> OpenSet<NODE>)? = null,
    var neighbors: ((NODE) -> List<Pair<NODE,Int>>)? = null,
    var heuristic: ((NODE) -> Int)? = null) {
    
    fun withNeighborsDistance(neighbors: (NODE) -> List<Pair<NODE,Int>>) = apply { this.neighbors = neighbors }
    fun withHeuristic(heuristic: (NODE) -> Int) = apply { this.heuristic = heuristic }
    fun usingPriorityQueue() = apply { this.newOpenSet = { OpenSetPQ(it) } }
    fun usingSimpleSet() = apply { this.newOpenSet = { OpenSetS(it) } }
    fun usingPriorityQueueAndSet() = apply { this.newOpenSet = { OpenSetPQS(it) } }
    fun build() = AStar(neighbors!!, heuristic!!, newOpenSet!!)
  }
  
  data class Result<NODE>(
    val start : NODE,
    private val sequence : Sequence<NODE>,
    private val openSet : OpenSet<NODE>) {
    val success: Boolean by lazy { length > 0 }
    val length: Int by lazy { sequence.count() }
    val visited: Int by lazy { openSet.pokes }
    val path: List<NODE> by lazy { sequence.toList().reversed() }
  }
  
  fun search(start:NODE, goal:NODE) = search(start) { it == goal }
  
  fun search(start:NODE, goals:Set<NODE>) = search(start) { goals.contains(it) }
  
  fun search(start:NODE, goalReached:(NODE) -> Boolean): Result<NODE> {
    val gScore = listOf(start).associateWith { 0 }.toMutableMap()
    val fScore = listOf(start).associateWith { heuristic(start) }.toMutableMap()
    val openSet = newOpenSet { fScore[it]!! }
    openSet.poke(start)
    val cameFrom = mutableMapOf<NODE,NODE>()
    
    while(openSet.isNotEmpty())
    {
      val current = openSet.peek()
      if(goalReached(current))
        return Result(start, generateSequence(current) { cameFrom[it]!! }.takeWhile { it != start }, openSet)
      for((neighbor, distance) in neighbors(current)) {
        val tentativeGScore = gScore.getOrDefault(current, Int.MAX_VALUE) + distance
        if(tentativeGScore < gScore.getOrDefault(neighbor, Int.MAX_VALUE)) {
          cameFrom[neighbor] = current
          gScore[neighbor] = tentativeGScore
          fScore[neighbor] = tentativeGScore + heuristic(neighbor)
          openSet.poke(neighbor)
        }
      }
    }
    return Result(start, sequenceOf(), openSet)
  }
  
  abstract class OpenSet<NODE> {
    var pokes = 0
    abstract fun isNotEmpty() : Boolean
    abstract fun peek() : NODE
    fun poke(node : NODE) {
      pokes++
      pokeImpl(node)
    }
    abstract fun pokeImpl(node : NODE)
  }
  
  private class OpenSetS<NODE>(val score:(NODE) -> Int) : OpenSet<NODE>() {
    var items = setOf<NODE>()
    override fun isNotEmpty() = items.isNotEmpty()
    override fun peek() : NODE {
      val selected = items.minByOrNull(score)
      items = items - selected!!
      return selected
    }
    override fun pokeImpl(node : NODE) {
      items = items + node
    }
  }
  
  private class OpenSetPQ<NODE>(val score:(NODE) -> Int) : OpenSet<NODE>() {
    var items = PriorityQueue(compareBy<NODE> { score(it) })
    override fun isNotEmpty() = items.isNotEmpty()
    override fun peek() : NODE = items.poll()!!
    override fun pokeImpl(node : NODE) {
      if(!items.contains(node))
        items.add(node)
    }
  }
  
  private class OpenSetPQS<NODE>(val score:(NODE) -> Int) : OpenSet<NODE>() {
    var pq = PriorityQueue(compareBy<NODE> { score(it) })
    val s = setOf<NODE>().toMutableSet()
    override fun isNotEmpty() = s.isNotEmpty()
    override fun peek() : NODE {
      val selected = pq.poll()
      s.remove(selected)
      return selected
    }
    override fun pokeImpl(node : NODE) {
      if(s.contains(node))
        return
      pq.add(node)
      s.add(node)
    }
  }
  
}