class Day14(input: List<String>) {
  private val rocks = input.flatMap(::readPath).toSet()
  private val depth = rocks.maxOf { it.y }
  private val source = XY(500, 0)
  val cave = Cave(rocks, source, depth)
  
  fun pourUntilVoid() = pourUntil { it.goesIntoVoid }
  fun pourUntilBlocked() = pourUntil { it.hasSourceBlocked }
  private fun pourUntil(condition: (Cave) -> Boolean) =
    generateSequence(cave) { it.pourOneSand() }.takeWhile { !condition(it) }.last().sandCount
  
  data class Cave(
    val blocks: Set<XY>, val source: XY, val depth: Int, val sandCount: Int = 0,
    val goesIntoVoid: Boolean = false, val hasSourceBlocked: Boolean = false
  ) {
    private val fallingDirections = listOf(XY(0, 1), XY(-1, 1), XY(1, 1))
    fun pourOneSand() = if (source in blocks)
      copy(hasSourceBlocked = true)
    else {
      val landing = generateSequence(source) { sand ->
        when (sand.y) {
          depth + 1 -> null
          else -> fallingDirections.map { sand + it }.firstOrNull { it !in blocks }
        }
      }.last()
      copy(blocks = blocks + landing, sandCount = sandCount + 1, goesIntoVoid = landing.y >= depth)
    }
  }
  
  data class XY(val x: Int, val y: Int) {
    operator fun plus(other: XY) = XY(x + other.x, y + other.y)
    operator fun minus(other: XY) = XY(x - other.x, y - other.y)
  }
  
  companion object {
    fun readPath(input: String) =
      input.split(" -> ").map(::readXY).zipWithNext().flatMap(::makeLine).toSet()
    
    fun readXY(input: String) =
      input.split(",").map { it.toInt() }.zipWithNext().map { XY(it.first, it.second) }.single()
    
    fun makeLine(p: Pair<XY, XY>) = if (p.first.x == p.second.x)
      range(p.first.y, p.second.y).map { XY(p.first.x, it) }
    else
      range(p.first.x, p.second.x).map { XY(it, p.first.y) }
    
    private fun range(a: Int, b: Int) = if (a < b) a..b else b..a
  }
  
  fun fasterPourUntilBlocked(): Int {
    val above = listOf(XY(-1, -1), XY(0, -1), XY(1, -1))
    val remainEmpty = generateSequence(true to rocks) { (canAdd, cannotFill) ->
      if (canAdd) {
        val candidates = cannotFill.map { it + XY(0, 1) }.toSet().filter { it.y <= depth + 1 } - cannotFill
        val safe = candidates.filter { candidate -> above.map { candidate + it }.all { it in cannotFill } }.toSet()
        safe.isNotEmpty() to cannotFill + safe
      } else null
    }.last().second
    val size = source.x + source.y
    val pyramid = (source.y..depth + 1).flatMap { y -> (size - y..size + y).map { x -> XY(x, y) } }.toSet()
    val canFill = pyramid - remainEmpty
    return canFill.count()
  }
}

