class Day14(input: List<String>) {
  
  private val blocks = input.flatMap(::readPath).toSet()
  private val depth = blocks.maxOf { it.y }
  private val source = XY(500,0)
  val cave = Cave(blocks, source, depth)
  
  fun pourUntilVoid() = generateSequence(cave) { it.pour() }.takeWhile { !it.goesIntoVoid }.last()
  fun pourUntilBlocked() = generateSequence(cave) { it.pour() }.takeWhile { !it.isBlockingSource }.last()
  fun maxSand() : Int {
    val above = listOf(XY(-1,-1), XY(0,-1),XY(1,-1))
    val remainEmpty = generateSequence(true to blocks) { (canAdd, cannotFill) ->
      if(canAdd) {
        val candidates = cannotFill.map { it+XY(0,1) }.toSet().filter { it.y <= depth+1 } - cannotFill
        val safe = candidates.filter { candidate -> above.map { candidate+it }.all { it in cannotFill } }.toSet()
        safe.isNotEmpty() to cannotFill + safe
      } else null
    }.last().second
    val size = source.x + source.y
    val pyramid = (source.y..depth+1).flatMap { y -> (size-y..size+y).map { x -> XY(x,y) } }.toSet()
    val canFill = pyramid - remainEmpty
    return canFill.count()
  }
  
  data class Cave(val blocks:Set<XY>, val source :XY, val depth :Int, val sandCount : Int = 0,
                  val goesIntoVoid : Boolean = false, val isBlockingSource : Boolean=false) {
    private val directions = listOf(XY(0, 1), XY(-1, 1), XY(1, 1))
    fun pour() : Cave = if(source in blocks)
      copy(isBlockingSource = true)
    else {
      val landing = generateSequence(source) { sand ->
        when (sand.y) {
          depth + 1 -> null
          else -> directions.map { sand + it }.firstOrNull { it !in blocks }
        }
      }.last()
      copy(blocks = blocks + landing, sandCount = sandCount + 1, goesIntoVoid = landing.y >= depth)
    }
  }
  
  data class XY(val x: Int, val y: Int) {
    operator fun plus(other: XY): XY = XY(x + other.x, y + other.y)
    operator fun minus(other: XY): XY = XY(x - other.x, y - other.y)
  }
  
  companion object {
    fun readPath(input:String) = input.split(" -> ").map(::readPoint).zipWithNext().flatMap(::makeLine).toSet()
    fun readPoint(input:String) : XY {
      val coords = input.split(",")
      return XY(coords[0].toInt(), coords[1].toInt())
    }
    fun makeLine(p:Pair<XY,XY>) = if(p.first.x == p.second.x)
      fromTo(p.first.y,p.second.y).map { XY(p.first.x,it) }
    else
      fromTo(p.first.x,p.second.x).map { XY(it,p.first.y) }
  
    private fun fromTo(start: Int, end: Int) = if(start < end) start..end else end..start
  }
}

