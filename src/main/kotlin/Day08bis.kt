class Day08bis(input: List<String>) {
  
  val heights = ArrayList(input.map { row ->
    ArrayList(row.map { it.code - '0'.code })
  })
  val width = heights.size
  val height = heights[0].size
  
  val trees = ArrayList(heights.mapIndexed { y, row ->
    ArrayList(row.mapIndexed { x, h -> Tree(x, y, h, this) })
  })
  private val allTrees = trees.flatten()
  
  init {
    for (tree in allTrees)
      for (edge in Edge.values())
        tree.initializeTowards(edge)
  }
  
  fun countVisible() = allTrees.count { it.isVisible() }
  fun scenicScore() = allTrees.maxOf { it.viewingDistance() }
  
  class Tree(val x: Int, val y: Int, private val height: Int, val forest: Day08bis) {
    fun isVisible() = maxHeightFrom.values.any { it < height }
    fun viewingDistance() = viewingDistanceTowards.values.reduce { a, b -> a * b }
    private val maxHeightFrom = mutableMapOf<Edge, Int>()
    private val viewingDistanceTowards = mutableMapOf<Edge, Int>()
    private val distancesForHeights = mutableMapOf<Edge, Map<Int,Int>>()
    private fun isInitializedFor(e: Edge) = maxHeightFrom.containsKey(e)
    internal fun initializeTowards(edge: Edge) {
      if (isInitializedFor(edge))
        return
      if (edge.contains(this)) {
        maxHeightFrom[edge] = -1
        viewingDistanceTowards[edge] = 0
        distancesForHeights[edge] = mapOf(10 to 0)
        return
      }
      val nextTreeTowardsEdge = edge.next(this)
      nextTreeTowardsEdge.initializeTowards(edge)
      
      maxHeightFrom[edge] = maxOf(nextTreeTowardsEdge.height, nextTreeTowardsEdge.maxHeightFrom[edge]!!)
      distancesForHeights[edge] =
        nextTreeTowardsEdge.distancesForHeights[edge]!!.mapValues {it.value + 1} + (nextTreeTowardsEdge.height to 1)
      viewingDistanceTowards[edge] = distancesForHeights[edge]!!.filter { it.key >= height }.values.min()
    }
  }
  
  enum class Edge {
    Top {
      override fun contains(t: Tree) = t.y == 0
      override fun next(t: Tree): Tree = t.forest.trees[t.y - 1][t.x]
    },
    Bottom {
      override fun contains(t: Tree) = t.y == t.forest.height - 1
      override fun next(t: Tree): Tree = t.forest.trees[t.y + 1][t.x]
    },
    Left {
      override fun contains(t: Tree) = t.x == 0
      override fun next(t: Tree): Tree = t.forest.trees[t.y][t.x - 1]
    },
    Right {
      override fun contains(t: Tree) = t.x == t.forest.width - 1
      override fun next(t: Tree): Tree = t.forest.trees[t.y][t.x + 1]
    };
    
    abstract fun contains(t: Tree): Boolean
    abstract fun next(t: Tree): Tree
  }
}