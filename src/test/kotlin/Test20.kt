import Day20.Mix
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test20 {

  private val example = listOf(
    "1",
    "2",
    "-3",
    "3",
    "-2",
    "0",
    "4",
  )

  @JvmStatic
  private fun moves() = listOf(
    0 to listOf(1, 2, -3, 3, -2, 0, 4),
    1 to listOf(2, 1, -3, 3, -2, 0, 4),
    2 to listOf(1, -3, 2, 3, -2, 0, 4),
    3 to listOf(1, 2, 3, -2, -3, 0, 4),
    4 to listOf(1, 2, -2, -3, 0, 3, 4),
    5 to listOf(-2, 1, 2, -3, 0, 3, 4),
    6 to listOf(-2, 1, 2, -3, 0, 3, 4),
    7 to listOf(-2, 1, 2, -3, 4, 0, 3),
  )

  @ParameterizedTest
  @MethodSource("moves")
  fun `from other test data - method source`(x : Pair<Int,List<Int>>) {
    val sim = Day20(example)
    val mix = generateSequence(Mix(sim.rules)) { it.next() }.elementAt(x.first)
    assertEquals(x.second.map { it.toLong() }, mix.values)
    assertEquals(x.second.indexOf(0), mix.zero)
  }


  @Test
  internal fun `part 1 in example`() {
    val sim = Day20(example)
    assertEquals(listOf(1, 2, 3, 3, 4, 0, 4), sim.rules.rules.map { it.delta })
    assertEquals(3, sim.sumOfGroveCoordinates())
  }

  @Test
  internal fun part1() {
    val sim = Day20(Util.getInputAsList(20))
    assertEquals(4066, sim.sumOfGroveCoordinates()) // too high
  }

  @Test
  internal fun part2() {
    val sim = Day20(Util.getInputAsList(20), 811589153)
    assertEquals(6704537992933L, sim.sumOfGroveCoordinates(10))
  }


}


