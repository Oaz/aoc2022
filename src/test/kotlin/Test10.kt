import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test10 {
  
  private val example = listOf(
    "noop",
    "addx 3",
    "addx -5",
  )
  
  private val expectedDeltasForExample = listOf(
    0,
    0,
    3,
    0,
    -5
  )
  @Test
  internal fun `read example`() {
    val sim = Day10(example)
    assertEquals(expectedDeltasForExample, sim.deltas)
  }
  
  private val expectedXsForExample = listOf(
    1,
    1,
    1,
    4,
    4,
    -1
  )
  @Test
  internal fun `compute X values for example`() {
    val sim = Day10(example)
    assertEquals(expectedXsForExample, sim.xs)
  }
  
  private val expectedStrengthsForExample = listOf(
    1,
    2,
    3,
    16,
    20,
    -6
  )
  @Test
  internal fun `compute strengths values for example`() {
    val sim = Day10(example)
    assertEquals(expectedStrengthsForExample, sim.strengths)
  }
  
  
  private val largerExample = listOf(
    "addx 15",
    "addx -11",
    "addx 6",
    "addx -3",
    "addx 5",
    "addx -1",
    "addx -8",
    "addx 13",
    "addx 4",
    "noop",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx -35",
    "addx 1",
    "addx 24",
    "addx -19",
    "addx 1",
    "addx 16",
    "addx -11",
    "noop",
    "noop",
    "addx 21",
    "addx -15",
    "noop",
    "noop",
    "addx -3",
    "addx 9",
    "addx 1",
    "addx -3",
    "addx 8",
    "addx 1",
    "addx 5",
    "noop",
    "noop",
    "noop",
    "noop",
    "noop",
    "addx -36",
    "noop",
    "addx 1",
    "addx 7",
    "noop",
    "noop",
    "noop",
    "addx 2",
    "addx 6",
    "noop",
    "noop",
    "noop",
    "noop",
    "noop",
    "addx 1",
    "noop",
    "noop",
    "addx 7",
    "addx 1",
    "noop",
    "addx -13",
    "addx 13",
    "addx 7",
    "noop",
    "addx 1",
    "addx -33",
    "noop",
    "noop",
    "noop",
    "addx 2",
    "noop",
    "noop",
    "noop",
    "addx 8",
    "noop",
    "addx -1",
    "addx 2",
    "addx 1",
    "noop",
    "addx 17",
    "addx -9",
    "addx 1",
    "addx 1",
    "addx -3",
    "addx 11",
    "noop",
    "noop",
    "addx 1",
    "noop",
    "addx 1",
    "noop",
    "noop",
    "addx -13",
    "addx -19",
    "addx 1",
    "addx 3",
    "addx 26",
    "addx -30",
    "addx 12",
    "addx -1",
    "addx 3",
    "addx 1",
    "noop",
    "noop",
    "noop",
    "addx -9",
    "addx 18",
    "addx 1",
    "addx 2",
    "noop",
    "noop",
    "addx 9",
    "noop",
    "noop",
    "noop",
    "addx -1",
    "addx 2",
    "addx -37",
    "addx 1",
    "addx 3",
    "noop",
    "addx 15",
    "addx -21",
    "addx 22",
    "addx -6",
    "addx 1",
    "noop",
    "addx 2",
    "addx 1",
    "noop",
    "addx -10",
    "noop",
    "noop",
    "addx 20",
    "addx 1",
    "addx 2",
    "addx 2",
    "addx -6",
    "addx -11",
    "noop",
    "noop",
    "noop",
  )
  
  @JvmStatic
  private fun expectedStrengthsForLargerExample() = listOf(
    20 to 420,
    60 to 1140,
    100 to 1800,
    140 to 2940,
    180 to 2880,
    220 to 3960,
  )
  @ParameterizedTest
  @MethodSource("expectedStrengthsForLargerExample")
  internal fun `compute strengths values for larger example`(x: Pair<Int,Int>) {
    val sim = Day10(largerExample)
    assertEquals(x.second, sim.strengths[x.first-1])
  }
  
  @Test
  internal fun `sum of strengths in larger example`() {
    val sim = Day10(largerExample)
    assertEquals(13140, sim.sumOfStrengths)
  }

  @Test
  internal fun part1() {
    val sim = Day10(Util.getInputAsList(10))
    assertEquals(13680, sim.sumOfStrengths)
  }
  
  @Test
  internal fun `part2 in larger example`() {
    val sim = Day10(largerExample)
    assertEquals(listOf(
      "##..##..##..##..##..##..##..##..##..##..",
      "###...###...###...###...###...###...###.",
      "####....####....####....####....####....",
      "#####.....#####.....#####.....#####.....",
      "######......######......######......####",
      "#######.......#######.......#######.....",
    ), sim.screen)
  }
  
  @Test
  internal fun part2() {
    val sim = Day10(Util.getInputAsList(10))
    assertEquals(listOf(
      "###..####..##..###..#..#.###..####.###..",
      "#..#....#.#..#.#..#.#.#..#..#.#....#..#.",
      "#..#...#..#....#..#.##...#..#.###..###..",
      "###...#...#.##.###..#.#..###..#....#..#.",
      "#....#....#..#.#....#.#..#....#....#..#.",
      "#....####..###.#....#..#.#....####.###..",
    ), sim.screen)
  }
}


