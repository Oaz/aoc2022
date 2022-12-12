import kotlin.math.absoluteValue

class Day12(input: List<String>) {
  
  val height = input.count()
  val width = input.first().count()
  
  data class XY(val x: Int, val y: Int) {
    operator fun plus(other: XY): XY = XY(x + other.x, y + other.y)
    operator fun minus(other: XY): XY = XY(x - other.x, y - other.y)
    fun manhattan() = x.absoluteValue + y.absoluteValue
  }
  
  data class Square(val position: XY, val elevation: Char, val text: Char, val host: Day12) {
    val neighbors = listOf(XY(0, 1), XY(0, -1), XY(1, 0), XY(-1, 0))
      .map { position + it }
      .filter { it.x in 0 until host.width && it.y in 0 until host.height }
  }
  
  private val area = input.mapIndexed { y, row ->
    row.mapIndexed { x, elevation ->
      when (elevation) {
        'S' -> Square(XY(x, y), 'a', elevation, this)
        'E' -> Square(XY(x, y), 'z', elevation, this)
        else -> Square(XY(x, y), elevation, elevation, this)
      }
    }
  }
  private val allSquares = area.flatten()
  private val start = allSquares.first { it.text == 'S' }
  private val end = allSquares.first { it.text == 'E' }
  private val aStar = AStar.on(allSquares).withHeuristic { 0 }
  private fun neighbors(square: Square, filter: (Square, Square) -> Boolean) =
    square.neighbors.map { area[it.y][it.x] }.filter { filter(square, it) }.map { it to 1 }
  
  fun fewestStepsToEnd() = aStar
    .withNeighborsDistance { neighbors(it) { from, to -> to.elevation < from.elevation + 2 } }.build()
    .search(start, end)
  
  fun fewestStepsToMultipleStartChoices() = aStar
    .withNeighborsDistance { neighbors(it) { from, to -> to.elevation > from.elevation - 2 } }.build()
    .search(end) { it.elevation == 'a' }
}
