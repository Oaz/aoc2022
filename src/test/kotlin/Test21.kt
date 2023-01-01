import Day21.*
import Day21.Companion.inverse
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test21 {

  private val example = listOf(
    "root: pppw + sjmn",
    "dbpl: 5",
    "cczh: sllz + lgvd",
    "zczc: 2",
    "ptdq: humn - dvpt",
    "dvpt: 3",
    "lfqf: 4",
    "humn: 5",
    "ljgn: 2",
    "sjmn: drzm * dbpl",
    "sllz: 4",
    "pppw: cczh / lfqf",
    "lgvd: ljgn * ptdq",
    "drzm: hmdt - zczc",
    "hmdt: 32",
  )

  @JvmStatic
  private fun monkeys() = listOf(
    "root: pppw + sjmn" to JobMonkey("root", "pppw", "+", "sjmn"),
    "dbpl: 235" to NumberMonkey("dbpl", 235)
  )

  @ParameterizedTest
  @MethodSource("monkeys")
  fun `create monkey`(x : Pair<String,Monkey>) {
    assertEquals(x.second, Day21.createMonkey(x.first))
  }
  
  
  @Test
  internal fun `inverse graph`() {
    val graph = mapOf( 'B' to listOf('A','C'), 'C' to listOf('B','D'), 'D' to listOf('B') )
    val expected = mapOf( 'A' to listOf('B'), 'B' to listOf('C','D'), 'C' to listOf('B'), 'D' to listOf('C') )
    assertEquals(expected, graph.inverse())
  }
  
  @Test
  internal fun `part 1 in example`() {
    val sim = Day21(example)
    assertEquals(152, sim.findRootYell())
  }

  @Test
  internal fun part1() {
    val sim = Day21(Util.getInputAsList(21))
    assertEquals(75147370123646, sim.findRootYell())
  }
  
  @Test
  internal fun `part 2 in example`() {
    val sim = Day21(example)
    assertEquals(301, sim.findHumanYell())
  }

  @Test
  internal fun part2() {
    val sim = Day21(Util.getInputAsList(21))
    assertEquals(3423279932937, sim.findHumanYell())
  }


}


