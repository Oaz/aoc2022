import Day16.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test16 {
  
  @JvmStatic
  private fun valveReading() = listOf(
    "Valve JJ has flow rate=21; tunnel leads to valve II" to ValveData("JJ", 21, listOf("II")),
    "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE" to ValveData("DD", 20, listOf("CC","AA","EE")),
  )
  @ParameterizedTest
  @MethodSource("valveReading")
  internal fun `read valves`(x: Pair<String, ValveData>) {
    assertEquals(x.second, Day16.readValveData(x.first))
  }
  
  
  
  private val example = listOf(
    "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB",
    "Valve BB has flow rate=13; tunnels lead to valves CC, AA",
    "Valve CC has flow rate=2; tunnels lead to valves DD, BB",
    "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE",
    "Valve EE has flow rate=3; tunnels lead to valves FF, DD",
    "Valve FF has flow rate=0; tunnels lead to valves EE, GG",
    "Valve GG has flow rate=0; tunnels lead to valves FF, HH",
    "Valve HH has flow rate=22; tunnel leads to valve GG",
    "Valve II has flow rate=0; tunnels lead to valves AA, JJ",
    "Valve JJ has flow rate=21; tunnel leads to valve II",
  )
  
  private val expectedPath = listOf(
    "move to DD" to 0,
    "open DD" to 0,
    "move to CC" to 20,
    "move to BB" to 20,
    "open BB" to 20,
    "move to AA" to 33,
    "move to II" to 33,
    "move to JJ" to 33,
    "open JJ" to 33,
    "move to II" to 54,
    "move to AA" to 54,
    "move to DD" to 54,
    "move to EE" to 54,
    "move to FF" to 54,
    "move to GG" to 54,
    "move to HH" to 54,
    "open HH" to 54,
    "move to GG" to 76,
    "move to FF" to 76,
    "move to EE" to 76,
    "open EE" to 76,
    "move to DD" to 79,
    "move to CC" to 79,
    "open CC" to 79,
    "wait" to 81,
    "wait" to 81,
    "wait" to 81,
    "wait" to 81,
    "wait" to 81,
    "wait" to 81,
  )
  
  @Test
  internal fun `part 1 in example`() {
    val sim = Day16(example)
    assertEquals(expectedPath, sim.searchAlone().path.map { it.info().text to it.flow.throughput })
    assertEquals(1651, sim.mostPressureAlone())
  }
  
  @Test
  internal fun part1() {
    val sim = Day16(Util.getInputAsList(16))
    assertEquals(1701, sim.mostPressureAlone())
  }
  
  private val expectedPathWithElephant = listOf(
    "You move to DD, elephant move to II" to 0,
    "You open DD, elephant move to JJ" to 0,
    "You move to EE, elephant open JJ" to 20,
    "You move to FF, elephant move to II" to 41,
    "You move to GG, elephant move to AA" to 41,
    "You move to HH, elephant move to BB" to 41,
    "You open HH, elephant open BB" to 41,
    "You move to GG, elephant move to CC" to 76,
    "You move to FF, elephant open CC" to 76,
    "You move to EE, elephant move to DD" to 78,
    "You open EE, elephant move to CC" to 78,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
    "You wait, elephant wait" to 81,
  )
  

  @Test
  internal fun `part 2 in example`() {
    val sim = Day16(example)
    assertEquals(expectedPathWithElephant, sim.searchInDuo().path.map { it.info().text to it.flow.throughput })
    assertEquals(1707, sim.mostPressureInDuo())
  }
  
  @Test
  internal fun part2() {
    val sim = Day16(Util.getInputAsList(16))
    assertEquals(2455, sim.mostPressureInDuo())
  }

}


