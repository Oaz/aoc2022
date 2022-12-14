import Day13.*
import Day14.XY
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test14 {
  
  @JvmStatic
  private fun points() = listOf(
    "498,48" to XY(498,48),
    "96,603" to XY(96,603),
  )
  @ParameterizedTest
  @MethodSource("points")
  internal fun `read points`(x: Pair<String, XY>) {
    assertEquals(x.second, Day14.readPoint(x.first))
  }
  
  @JvmStatic
  private fun lines() = listOf(
    (XY(498,4) to XY(498,6)) to listOf( XY(498,4),XY(498,5),XY(498,6)),
    (XY(498,6) to XY(495,6)) to listOf( XY(495,6),XY(496,6),XY(497,6),XY(498,6)),
  )
  @ParameterizedTest
  @MethodSource("lines")
  internal fun `read lines`(x: Pair<Pair<XY,XY>, List<XY>>) {
    assertEquals(x.second, Day14.makeLine(x.first))
  }
  
  @JvmStatic
  private fun paths() = listOf(
    "498,4 -> 498,6 -> 496,6" to setOf(
      XY(498,4),XY(498,5),XY(498,6),
      XY(497,6),XY(496,6),
    ),
  )
  @ParameterizedTest
  @MethodSource("paths")
  internal fun `read paths`(x: Pair<String, Set<XY>>) {
    assertEquals(x.second, Day14.readPath(x.first))
  }
  
  private val example = listOf(
    "498,4 -> 498,6 -> 496,6",
    "503,4 -> 502,4 -> 502,9 -> 494,9",
  )
  
  @Test
  internal fun `read cave`() {
    val sim = Day14(example)
    assertEquals(setOf(
      XY(498,4),XY(498,5),XY(498,6),
      XY(497,6),XY(496,6),
      XY(503,4),XY(502,4),
      XY(502,5),XY(502,6),XY(502,7),XY(502,8),XY(502,9),
      XY(501,9),XY(500,9),XY(499,9),XY(498,9),XY(497,9),XY(496,9),XY(495,9),XY(494,9),
    ), sim.cave.blocks)
    assertEquals(9, sim.cave.depth)
  }
  
  @Test
  internal fun `pour into example`() {
    val sim = Day14(example)
    val cave1 = sim.cave.pour()
    assertEquals(setOf(XY(500,8)), cave1.blocks - sim.cave.blocks)
    val cave2 = cave1.pour()
    assertEquals(setOf(XY(499,8)), cave2.blocks - cave1.blocks)
    val cave3 = cave2.pour()
    assertEquals(setOf(XY(501,8)), cave3.blocks - cave2.blocks)
    val cave4 = cave3.pour()
    assertEquals(setOf(XY(500,7)), cave4.blocks - cave3.blocks)
    val cave5 = cave4.pour()
    assertEquals(setOf(XY(498,8)), cave5.blocks - cave4.blocks)
    val cave6 = cave5.pour()
    assertEquals(setOf(XY(499,7)), cave6.blocks - cave5.blocks)
    assertEquals(6, cave6.sandCount)
  }

  @Test
  internal fun `part 1 in example`() {
    val cave = Day14(example).pourUntilVoid()
    assertEquals(24, cave.sandCount)
  }

  @Test
  internal fun part1() {
    val cave = Day14(Util.getInputAsList(14)).pourUntilVoid()
    assertEquals(719, cave.sandCount)
  }

  @Test
  internal fun `part 2 in example`() {
    val sim = Day14(example)
    assertEquals(93, sim.pourUntilBlocked().sandCount)
    assertEquals(93, sim.maxSand())
  }

  @Test
  internal fun part2() {
    val sim = Day14(Util.getInputAsList(14))
    assertEquals(23390, sim.maxSand())
  }

}


