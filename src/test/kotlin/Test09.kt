import Day09.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test09 {
  
  private val example = listOf(
    "R 4",
    "U 4",
    "L 3",
    "D 1",
    "R 4",
    "D 1",
    "L 5",
    "R 2",
  )
  
  private val expectedMotionForExample = listOf(
    Motion(Direction.Right,4),
    Motion(Direction.Up,4),
    Motion(Direction.Left,3),
    Motion(Direction.Down,1),
    Motion(Direction.Right,4),
    Motion(Direction.Down,1),
    Motion(Direction.Left,5),
    Motion(Direction.Right,2),
  )
  @Test
  internal fun `read example`() {
    val sim = Day09(example)
    assertEquals(expectedMotionForExample, sim.motion)
  }
  
  data class ExpectedMoves(
    val motion: Motion,
    val expectedMoves: List<XY>,
  )
  @JvmStatic
  private fun expectedMoves() = listOf(
    ExpectedMoves(Motion(Direction.Right,4), listOf(XY(1,0),XY(1,0),XY(1,0),XY(1,0))),
    ExpectedMoves(Motion(Direction.Up,4), listOf(XY(0,1),XY(0,1),XY(0,1),XY(0,1))),
    ExpectedMoves(Motion(Direction.Left,3), listOf(XY(-1,0),XY(-1,0),XY(-1,0))),
    ExpectedMoves(Motion(Direction.Down,1), listOf(XY(0,-1))),
  )
  @ParameterizedTest
  @MethodSource("expectedMoves")
  internal fun `compute moves`(x: ExpectedMoves) {
    assertEquals(x.expectedMoves, x.motion.moves())
  }
  
  private val expectedHeadPositionsForExample = listOf(
    XY(0,0),
    XY(1,0),
    XY(2,0),
    XY(3,0),
    XY(4,0),
    XY(4,1),
    XY(4,2),
    XY(4,3),
    XY(4,4),
    XY(3,4),
    XY(2,4),
    XY(1,4),
    XY(1,3),
    XY(2,3),
    XY(3,3),
    XY(4,3),
    XY(5,3),
    XY(5,2),
    XY(4,2),
    XY(3,2),
    XY(2,2),
    XY(1,2),
    XY(0,2),
    XY(1,2),
    XY(2,2),
  )
  @Test
  internal fun `move head in example`() {
    val sim = Day09(example)
    assertEquals(expectedHeadPositionsForExample, sim.shortRopes().map { it.head })
  }
  
  @Test
  internal fun `tail does not move when head still in range`() {
    assertEquals(Physics(XY(1,0),XY(0,0)), Physics(XY(0,0),XY(0,0)).moveHead(XY(1,0)))
    assertEquals(Physics(XY(1,0),XY(1,1)), Physics(XY(0,0),XY(1,1)).moveHead(XY(1,0)))
  }
  
  @Test
  internal fun `tail replaces head when head move is horizontal or vertical`() {
    assertEquals(Physics(XY(2,0),XY(1,0)), Physics(XY(1,0),XY(0,0)).moveHead(XY(1,0)) )
    assertEquals(Physics(XY(2,1),XY(1,1)), Physics(XY(1,1),XY(0,1)).moveHead(XY(1,0)) )
  }
  
  @Test
  internal fun `tail moves halfway when delta to new head is horizontal or vertical`() {
    assertEquals(Physics(XY(3,5),XY(3,4)), Physics(XY(4,4),XY(3,3)).moveHead(XY(-1,1)))
    assertEquals(Physics(XY(4,6),XY(4,5)), Physics(XY(5,5),XY(4,4)).moveHead(XY(-1,1)))
  }
  
  @Test
  internal fun `in other cases, tail moves same as head`() {
    assertEquals(Physics(XY(2,2),XY(1,1)), Physics(XY(1,1),XY(0,0)).moveHead(XY(1,1)) )
    assertEquals(Physics(XY(3,3),XY(2,2)), Physics(XY(2,2),XY(1,1)).moveHead(XY(1,1)) )
  }
  
  private val expectedTailPositionsForExample = listOf(
    XY(0,0),
    XY(0,0),
    XY(1,0),
    XY(2,0),
    XY(3,0),
    XY(3,0),
    XY(4,1),
    XY(4,2),
    XY(4,3),
    XY(4,3),
    XY(3,4),
    XY(2,4),
    XY(2,4),
    XY(2,4),
    XY(2,4),
    XY(3,3),
    XY(4,3),
    XY(4,3),
    XY(4,3),
    XY(4,3),
    XY(3,2),
    XY(2,2),
    XY(1,2),
    XY(1,2),
    XY(1,2),
  )
  @Test
  internal fun `move tail in example`() {
    val sim = Day09(example)
    assertEquals(expectedTailPositionsForExample, sim.shortRopes().map { it.tail })
  }
  
  @Test
  internal fun `part1 in example`() {
    val sim = Day09(example)
    assertEquals(13, sim.uniqueShortRopeTailPositions())
  }
  
  @Test
  internal fun part1() {
    val sim = Day09(Util.getInputAsList(9))
    assertEquals(6190, sim.uniqueShortRopeTailPositions())
  }

  
  @Test
  internal fun `part2 in example`() {
    val sim = Day09(example)
    assertEquals(1, sim.uniqueLongRopeTailPositions())
  }
  
  private val largerExample = listOf(
    "R 5",
    "U 8",
    "L 8",
    "D 3",
    "R 17",
    "D 10",
    "L 25",
    "U 20",
  )
  
  @Test
  internal fun `part2 in larger example`() {
    val sim = Day09(largerExample)
    assertEquals(36, sim.uniqueLongRopeTailPositions())
  }
  
  @Test
  internal fun part2() {
    val sim = Day09(Util.getInputAsList(9))
    assertEquals(2516, sim.uniqueLongRopeTailPositions())
  }
}


