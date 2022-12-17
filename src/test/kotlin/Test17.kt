import Day17.*
import Day17.Companion.emptyTower
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test17 {
  
  @JvmStatic
  private fun zonePushing() = listOf(
    ("..####." to Direction.Right) to "...####",
    ("..####." to Direction.Left) to ".####..",
    (".####.." to Direction.Right) to "..####.",
    (".####.." to Direction.Left) to "####...",
    ("...####" to Direction.Right) to "...####",
    ("####..." to Direction.Left) to "####...",
  )
  
  @ParameterizedTest
  @MethodSource("zonePushing")
  internal fun `push zones`(x: Pair<Pair<String, Direction>, String>) {
    assertEquals(Zone.make(x.second), Zone.make(x.first.first).push(x.first.second))
  }
  
  private val zf = listOf(
    Zone.make(),
    Zone.make(
      "..####.",
    ),
    Zone.make(
      "...####",
    ),
    Zone.make(
      "..#....",
      ".###...",
      "..#....",
    ),
    Zone.make(
      "...#...",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
//5
      "..#....",
      "..#....",
      "###....",
    ),
    Zone.make(
      "...#...",
      "...#...",
      ".###...",
    ),
    Zone.make(
      "..#....",
      "..#....",
      "####...",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "##.....",
      "##.....",
    ),
    Zone.make(
      "....#..",
      "....#..",
      "....##.",
      "....##.",
      "..####.",
      ".###...",
      "..#....",
    ),
    Zone.make(//10
      "....#..",
      "....#..",
      "....##.",
      "##..##.",
      "######.",
      ".###...",
      "..#....",
    ),
  )
  
  @JvmStatic
  private fun zoneFalling() = listOf(
    (Falling.appear(0, 0, zf[0]) to Direction.Right) to Falling(Couple(zf[2], 3, zf[0]), 0, 0),
    (Falling(Couple(zf[2], 3, zf[0]), 0, 0) to Direction.Right) to Falling(Couple(zf[2], 2, zf[0]), 0, 0),
    (Falling(Couple(zf[2], 2, zf[0]), 0, 0) to Direction.Right) to Falling(Couple(zf[2], 1, zf[0]), 0, 0),
    (Falling(Couple(zf[2], 1, zf[0]), 0, 0) to Direction.Left) to Falling.appear(1, 0, zf[1]),
    (Falling(Couple(zf[3], 3, zf[1]), 1, 0) to Direction.Right) to Falling.appear(2, 0, zf[4]),
    (Falling(Couple(zf[5], 4, zf[4]), 2, 0) to Direction.Right) to Falling(Couple(zf[6], 3, zf[4]), 2, 0),
    (Falling(Couple(zf[6], 3, zf[4]), 2, 0) to Direction.Left) to Falling(Couple(zf[5], 2, zf[4]), 2, 0),
    (Falling(Couple(zf[5], 2, zf[4]), 2, 0) to Direction.Left) to Falling.appear(3, 0, zf[7]),
    (Falling(Couple(zf[8], -3, zf[9]), 4, 0) to Direction.Left) to Falling.appear(0, 0, zf[10]),
  )
  
  @ParameterizedTest
  @MethodSource("zoneFalling")
  internal fun `falling rocks`(x: Pair<Pair<Falling, Direction>, Falling>) {
    assertEquals(x.second, x.first.first.pushAndFall(x.first.second))
  }
  
  private val example = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
  
  private val zl = listOf(
    Zone.make(
      "..####.",
    ),
    Zone.make(
      "...#...",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "..#....",
      "..#....",
      "####...",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "....#..",
      "..#.#..",
      "..#.#..",
      "#####..",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "....##.",
      "....##.",
      "....#..",
      "..#.#..",
      "..#.#..",
      "#####..",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(//5
      ".####..",
      "....##.",
      "....##.",
      "....#..",
      "..#.#..",
      "..#.#..",
      "#####..",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "..#....",
      ".###...",
      "..#....",
      ".####..",
      "....##.",
      "....##.",
      "....#..",
      "..#.#..",
      "..#.#..",
      "#####..",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "....#..",
      "....#..",
      "....##.",
      "....##.",
      "..####.",
      ".###...",
      "..#....",
      ".####..",
      "....##.",
      "....##.",
      "....#..",
      "..#.#..",
      "..#.#..",
      "#####..",
      "..###..",
      "...#...",
      "..####.",
    ),
    Zone.make(
      "....#..",
      "....#..",
      "....##.",
      "##..##.",
      "######.",
      ".###...",
      "..#....",
      ".####..",
      "....##.",
      "....##.",
      "....#..",
      "..#.#..",
      "..#.#..",
      "#####..",
      "..###..",
      "...#...",
      "..####.",
    ),
  )
  
  @JvmStatic
  private fun zoneLanding() = listOf(
    Falling.appear(0, 0, emptyTower) to Falling.appear(1, 4, zl[0]),
    Falling.appear(1, 4, zl[0]) to Falling.appear(2, 8, zl[1]),
    Falling.appear(2, 8, zl[1]) to Falling.appear(3, 13, zl[2]),
    Falling.appear(3, 13, zl[2]) to Falling.appear(4, 20, zl[3]),
    Falling.appear(4, 20, zl[3]) to Falling.appear(0, 24, zl[4]),
    Falling.appear(0, 24, zl[4]) to Falling.appear(1, 28, zl[5]),
    Falling.appear(1, 28, zl[5]) to Falling.appear(2, 32, zl[6]),
    Falling.appear(4, 3, zl[7]) to Falling.appear(0, 12, zl[8]),
  )
  
  @ParameterizedTest
  @MethodSource("zoneLanding")
  internal fun `landing rocks`(x: Pair<Falling, Falling>) {
    val sim = Day17(example)
    assertEquals(x.second, sim.land(x.first))
  }
  
  @Test
  internal fun `part 1 in example`() {
    val sim = Day17(example)
    assertEquals(3068, sim.findHeight(2022))
  }
  
  @Test
  internal fun part1() {
    val sim = Day17(Util.getInputAsString(17))
    assertEquals(3173, sim.findHeight(2022))
  }
  
  @Test
  internal fun `part 2 in example`() {
    val sim = Day17(example)
    assertEquals(1514285714288, sim.findHeight(1000000000000))
  }
  
  @Test
  internal fun part2() {
    val sim = Day17(Util.getInputAsString(17))
    assertEquals(1570930232582, sim.findHeight(1000000000000))
  }

}


