import kotlin.math.absoluteValue

class Day18(input: List<String>) {
  
  private val positions = input.map { readPosition(it) }
  private val xBounds = bound { it.x }
  private val yBounds = bound { it.y }
  private val zBounds = bound { it.z }
  private fun bound(f: (XYZ) -> Int) = positions.map { f(it) }.let { it.minOrNull()!! - 1..it.maxOrNull()!! + 1 }
  private fun cube(position: XYZ) = Cube(position, xBounds, yBounds, zBounds)
  private val droplet = positions.map { cube(it) }.toSet()
  fun totalVisibleFaces() = droplet.sumOf { it.visibleFaces(droplet) }
  
  fun exteriorFaces(): Int {
    val zone = sequence {
      for (x in xBounds) for (y in yBounds) for (z in zBounds) yield(cube(XYZ(x, y, z)))
    }.toList()
    val dropletAndInside = droplet.toMutableSet()
    val outside = zone.filter { it.boundaryDistance == 0 }.toMutableSet()
    val finder = AStar.on<Cube>()
      .withHeuristic { 0 }  // it.boundaryDistance is worse
      .withNeighborsDistance { c -> c.neighbors.filter { it !in dropletAndInside }.map { it to 1 } }.build()
    for (unknown in zone.filter { it !in dropletAndInside && it.boundaryDistance > 0 }) {
      val result = finder.search(unknown) { it in outside }
      if (result.success)
        outside.add(unknown)
      else
        dropletAndInside.add(unknown)
    }
    return dropletAndInside.sumOf { it.visibleFaces(dropletAndInside) }
  }
  
  data class Cube(val position: XYZ, val xBounds: IntRange, val yBounds: IntRange, val zBounds: IntRange) {
    private val neighborPositions by lazy { directions.map { position + it } }
    private val boundaryNeighborPositions by lazy {
      listOf(
        XYZ(xBounds.first, position.y, position.z),
        XYZ(xBounds.last, position.y, position.z),
        XYZ(position.x, yBounds.first, position.z),
        XYZ(position.x, yBounds.last, position.z),
        XYZ(position.x, position.y, zBounds.first),
        XYZ(position.x, position.y, zBounds.last),
      )
    }
    val boundaryDistance by lazy { boundaryNeighborPositions.minOf { (it - position).manhattan } }
    val neighbors by lazy { neighborPositions.map { Cube(it, xBounds, yBounds, zBounds) } }
    fun visibleFaces(droplet: Set<Cube>) = 6 - neighbors.count { it in droplet }
  }
  
  data class XYZ(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: XYZ) = XYZ(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: XYZ) = XYZ(x - other.x, y - other.y, z - other.z)
    val manhattan by lazy { x.absoluteValue + y.absoluteValue + z.absoluteValue }
  }
  
  companion object {
    fun readPosition(input: String): XYZ {
      val n = regex.findAll(input).map { it.value.toInt() }.toList()
      return XYZ(n[0], n[1], n[2])
    }
    
    private val regex = """[-\d]+""".toRegex()
    
    val directions = listOf(
      XYZ(1, 0, 0),
      XYZ(-1, 0, 0),
      XYZ(0, 1, 0),
      XYZ(0, -1, 0),
      XYZ(0, 0, 1),
      XYZ(0, 0, -1),
    )
  }
}