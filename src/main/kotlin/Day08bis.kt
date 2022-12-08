import java.util.EnumMap

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
      for (edge in Edge.values())
        edge.forEach(this) {
          it.buildSide(edge)
        }
  }
  
  fun countVisible() = allTrees.count { it.isVisible() }
  fun scenicScore() = allTrees.maxOf { it.viewingDistance() }
  
  class Tree(val x: Int, val y: Int, val height: Int, val forest: Day08bis) {
    fun isVisible() = sides.values.map { it.maxHeight }.any { it < height }
    fun viewingDistance() = sides.values.map { it.viewingDistance }.reduce { a, b -> a * b }
    val sides = EnumMap<Edge, TreeSide>(Edge::class.java)
    fun buildSide(edge : Edge) { sides[edge] = TreeSide.initialize(this, edge) }
  }
  
  class TreeSide(val maxHeight : Int, val viewingDistance : Int, private val distancesForHeights : IntArray) {
    companion object {
      private val emptyHeights = intArrayOf(0,0,0,0,0,0,0,0,0,0)
      fun initialize(tree: Tree, edge: Edge): TreeSide {
        if (edge.contains(tree))
          return TreeSide(-1, 0, emptyHeights)
        val nextTreeTowardsEdge = edge.next(tree)
        val nextSide = nextTreeTowardsEdge.sides[edge]!!
        val distances = nextSide.distancesForHeights.map { it + 1 }.toIntArray()
        distances[nextTreeTowardsEdge.height] = 1
        return TreeSide(
          maxOf(nextTreeTowardsEdge.height, nextSide.maxHeight),
          distances.slice(tree.height..9).min(),
          distances
        )
      }
    }
  }
  
  enum class Direction {
    Upwards {
      override fun step(): Int = 1
      override fun progression(forest: Day08bis, orientation: Orientation) = 0 until orientation.size(forest)
      override fun bound(forest: Day08bis, orientation: Orientation) : Int = 0
    },
    Downwards {
      override fun step(): Int = -1
      override fun progression(forest: Day08bis, orientation: Orientation) = orientation.size(forest)-1 downTo 0
      override fun bound(forest: Day08bis, orientation: Orientation) : Int = orientation.size(forest) - 1
    };
    abstract fun step() : Int
    abstract fun progression(forest: Day08bis, orientation: Orientation) : IntProgression
    abstract fun bound(forest: Day08bis, orientation: Orientation) : Int
  }

  enum class Orientation {
    XY {
      override fun forEach(forest: Day08bis, direction : Direction, action: (Tree) -> Unit) {
        for (x in direction.progression(forest,XY))
          for (y in direction.progression(forest,YX)) action(forest.trees[y][x])
      }
      override fun next(direction : Direction, t: Tree): Tree = t.forest.trees[t.y][t.x - direction.step()]
      override fun first(t: Tree): Int = t.x
      override fun size(forest: Day08bis): Int = forest.width
    },
    YX {
      override fun forEach(forest: Day08bis, direction : Direction, action: (Tree) -> Unit) {
        for (y in direction.progression(forest,YX))
          for (x in direction.progression(forest,XY)) action(forest.trees[y][x])
      }
      override fun next(direction : Direction, t: Tree): Tree = t.forest.trees[t.y - direction.step()][t.x]
      override fun first(t: Tree): Int = t.y
      override fun size(forest: Day08bis): Int = forest.height
    };
    abstract fun forEach(forest: Day08bis, direction : Direction, action: (Tree) -> Unit)
    abstract fun next(direction : Direction, t: Tree): Tree
    abstract fun first(t: Tree): Int
    abstract fun size(forest: Day08bis): Int
  }
  
  enum class Edge {
    Top {
      override fun direction() = Direction.Upwards
      override fun orientation() = Orientation.YX
    },
    Bottom {
      override fun direction() = Direction.Downwards
      override fun orientation() = Orientation.YX
    },
    Left {
      override fun direction() = Direction.Upwards
      override fun orientation() = Orientation.XY
    },
    Right {
      override fun direction() = Direction.Downwards
      override fun orientation() = Orientation.XY
    };
    fun contains(t: Tree) = orientation().first(t) == direction().bound(t.forest,orientation())
    fun next(t: Tree) = orientation().next(direction(), t)
    fun forEach(forest: Day08bis, action: (Tree) -> Unit)= orientation().forEach(forest, direction(), action)
    abstract fun direction() : Direction
    abstract fun orientation() : Orientation
  }
}