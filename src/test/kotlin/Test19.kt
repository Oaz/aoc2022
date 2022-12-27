import Day19.*
import Day19.Kind.*
import Day19.Vector.Companion.zeroes
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test19 {
  
  @JvmStatic
  private fun blueprints() = listOf(
    "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian."
        to Blueprint(1, 4, 2, 3, 14, 2, 7),
    "Blueprint 12: Each ore robot costs 20 ore. Each clay robot costs 31 ore. Each obsidian robot costs 15 ore and 8 clay. Each geode robot costs 30 ore and 12 obsidian."
        to Blueprint(12, 20, 31, 15, 8, 30, 12),
  )
  
  @ParameterizedTest
  @MethodSource("blueprints")
  internal fun `read blueprints`(x: Pair<String, Blueprint>) {
    assertEquals(x.second, Day19.readBlueprint(x.first))
  }
  
  private val blueprint1 = Blueprint(0, 4, 2, 3, 14, 2, 7)
  private val blueprint2 = Blueprint(0, 2, 3, 3, 8, 3, 12)
  private val blueprint3 = Blueprint(0, 3, 3, 2, 15, 3, 9)
  private val blueprint4 = Blueprint(0, 4, 4, 4, 12, 4, 19)
  private val blueprint5 = Blueprint(0, 4, 4, 4, 14, 3, 16)

  data class Scenario(
    val blueprint:Blueprint,
    val material: Vector,
    val robots: Vector,
    val next: List<Kind>,
    val expectedTime: Int = 0,
    val expectedMaterial: Vector? = null
  )

  @JvmStatic
  private fun scenarios() = listOf(
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore), 5, Ore.unit),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Clay), 3, Ore.unit),
    Scenario(blueprint1, Ore.unit, Ore.unit * 2, listOf(Ore), 3, Ore.unit * 3),
    Scenario(blueprint1, Ore.unit, Ore.unit * 2, listOf(Clay), 2, Ore.unit * 3),
    Scenario(blueprint1, Vector(2, 14, 0, 0), Ore.unit, listOf(Ore), 3, Vector(1, 14, 0, 0)),
    Scenario(blueprint1, Vector(2, 14, 0, 0), Ore.unit, listOf(Clay), 1, Vector(1, 14, 0, 0)),
    Scenario(blueprint1, Vector(2, 14, 0, 0), Ore.unit, listOf(Obsidian), 2, Ore.unit),
    Scenario(blueprint1, zeroes, Ore.unit + Clay.unit, listOf(Ore), 5, Vector(1, 5, 0, 0)),
    Scenario(blueprint1, zeroes, Ore.unit + Clay.unit, listOf(Clay), 3, Vector(1, 3, 0, 0)),
    Scenario(blueprint1, zeroes, Ore.unit + Clay.unit, listOf(Obsidian), 15, Vector(14 - 3 + 1, 1, 0, 0)),
    Scenario(blueprint1, zeroes, Ore.unit + Obsidian.unit, listOf(Ore), 5, Vector(1, 0, 4 + 1, 0)),
    Scenario(blueprint1, zeroes, Ore.unit + Obsidian.unit, listOf(Clay), 3, Vector(1, 0, 2 + 1, 0)),
    Scenario(blueprint1, zeroes, Ore.unit + Obsidian.unit, listOf(Geode), 8, Vector(7 - 2 + 1, 0, 1, 0)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Clay, Clay), 5, Vector(1, 2, 0, 0)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Clay, Clay, Clay), 7, Vector(1, 6, 0, 0)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Ore), 8, Ore.unit * 3),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Ore, Ore), 10, Ore.unit * 5),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Ore, Ore, Ore), 11, Ore.unit * 5),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Ore, Ore, Ore), 11, Ore.unit * 5),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay), 13, Vector(3,21,0,0)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Geode), 20, Vector(3,14,7,0)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Geode, Geode, Geode, Geode, Geode), 31, Vector(6, 77, 5, 47)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay), 12, Vector(3, 15, 0, 0)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Obsidian, Geode, Geode, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Geode, Geode, Obsidian), 32, Vector(2,55,12,54)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Geode, Geode, Geode, Geode, Geode), 31, Vector(6,77,5,47)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Geode, Geode, Geode, Geode, Geode, Ore), 32, Vector(4,84,10,56)),
    
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian), 15, Vector(3,6,7,0)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Clay, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode), 24, Vector(3, 36, 11, 12)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Geode), 18, Vector(9,24,7,0)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian), 19, Vector(9,22,11,1)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode), 21, Vector(12,34,9,3)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Geode), 23, Vector(15,46,7,7)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Geode, Geode), 25, Vector(18,58,5,13)),
    Scenario(blueprint2, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Obsidian, Geode, Geode, Obsidian), 32, Vector(9,32,24,62)),
    
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay), 6, Vector(2,0,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Clay), 8, Vector(3,2,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay), 9, Vector(2,4,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay), 11, Vector(3,10,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Ore), 8, Vector(3,2,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Ore, Clay), 9, Vector(3,3,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Ore, Clay, Clay), 10, Vector(3,5,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Ore, Clay, Clay, Clay), 11, Vector(3,8,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay), 11, Vector(5,6,0,0)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Obsidian, Geode), 24, Vector(13, 29, 8, 2)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Obsidian, Geode, Obsidian), 24, Vector(9,16,10,4)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Geode), 24, Vector(8,47,3,3)),
    Scenario(blueprint3, zeroes, Ore.unit, listOf(Ore, Clay, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Obsidian, Geode, Obsidian), 32, Vector(15,29,21,40)),
  
    Scenario(blueprint4, zeroes, Ore.unit, listOf(Ore, Ore, Ore, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Obsidian, Geode, Geode, Obsidian), 32, Vector(13,29,16,10)),
  
    Scenario(blueprint5, zeroes, Ore.unit, listOf(Ore, Ore, Ore, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Clay, Obsidian, Obsidian, Clay, Obsidian, Geode, Obsidian, Obsidian, Geode, Geode, Obsidian), 32, Vector(16,32,14,10)),
  )

  @ParameterizedTest
  @MethodSource("scenarios")
  internal fun `run scenario`(x: Scenario) {
    val end = Snapshot(x.blueprint, x.material, x.robots).next(x.next)!!
    assertEquals(x.expectedTime, end.time)
    assertEquals(x.expectedMaterial, end.material)
  }

  @JvmStatic
  private fun infinite_scenarios() = listOf(
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Obsidian)),
    Scenario(blueprint1, zeroes, Ore.unit, listOf(Geode)),
  )

  @ParameterizedTest
  @MethodSource("infinite_scenarios")
  internal fun `run infinite scenarios`(x: Scenario) {
    val end = Snapshot(blueprint1, x.material, x.robots).next(x.next)
    assertEquals(null, end)
  }
  
  @JvmStatic
  private fun geodes() = listOf(
    Blueprint(0, 4, 2, 3, 14, 2, 7) to
        (listOf(Clay, Clay, Clay, Obsidian, Clay, Obsidian, Geode, Geode, Obsidian, Geode, Geode)
            to 9),
    Blueprint(0, 2, 3, 3, 8, 3, 12) to
        (listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Clay, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Obsidian, Obsidian)
            to 12),
    Blueprint(0, 3, 3, 2, 15, 3, 9) to
        (listOf(Ore, Clay, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Obsidian, Geode, Obsidian, Obsidian)
            to 4),
    Blueprint(0, 4, 4, 4, 12, 4, 19) to
        (listOf(Clay, Clay, Obsidian, Ore, Clay, Obsidian, Ore, Ore)
            to 0),
    Blueprint(0, 4, 4, 4, 14, 3, 16) to
        (listOf(Clay, Ore, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian)
            to 0),
    Blueprint(0, 2, 3, 2, 17, 3, 19) to
        (listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Obsidian, Obsidian, Obsidian)
            to 2),
  )
  
  @ParameterizedTest
  @MethodSource("geodes")
  internal fun `compute geode numbers in 24 minutes`(x: Pair<Blueprint, Pair<List<Kind>, Int>>) {
    assertEquals(x.second, Finder(x.first, 24, Day19::strategy1).best)
  }
  
  
  private val example = listOf(
    "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.",
    "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.",
  )
  
  @Test
  internal fun `part 1 in example`() {
    val sim = Day19(example)
    assertEquals(33, sim.qualityLevelsPart1())
  }
  
  
  @Test
  internal fun part1() {
    val sim = Day19(Util.getInputAsList(19))
    assertEquals(1427, sim.qualityLevelsPart1())
  }
  
  @JvmStatic
  private fun geodes2() = listOf(
    (Blueprint(0, 4, 2, 3, 14, 2, 7) to Day19::strategy2) to
        (listOf(Ore, Clay, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Obsidian, Geode, Geode, Geode, Geode, Geode, Obsidian, Geode, Geode, Geode, Geode, Geode)
            to 56),
    (Blueprint(0, 2, 3, 3, 8, 3, 12) to Day19::strategy1) to
        (listOf(Ore, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Obsidian, Geode, Geode, Obsidian, Obsidian)
            to 62),
    (Blueprint(0, 3, 3, 2, 15, 3, 9) to Day19::strategy1) to
        (listOf(Ore, Clay, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Clay, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Obsidian, Geode, Geode, Geode, Obsidian, Geode, Obsidian, Obsidian)
            to 40),
    (Blueprint(0, 4, 4, 4, 12, 4, 19) to Day19::strategy1) to
        (listOf(Ore, Ore, Ore, Clay, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Clay, Obsidian, Obsidian, Obsidian, Obsidian, Geode, Obsidian, Obsidian, Geode, Geode, Obsidian, Obsidian)
            to 10),
    (Blueprint(0, 4, 4, 4, 14, 3, 16) to Day19::strategy3) to
        (listOf(Ore, Ore, Clay, Ore, Clay, Clay, Clay, Clay, Clay, Obsidian, Clay, Obsidian, Clay, Obsidian, Obsidian, Geode, Obsidian, Obsidian, Obsidian, Geode, Geode, Obsidian, Obsidian)
            to 11),
  )
  
  @ParameterizedTest
  @MethodSource("geodes2")
  internal fun `compute geode numbers in 32 minutes`(x: Pair<Pair<Blueprint,(Snapshot)->List<Kind>>, Pair<List<Kind>, Int>>) {
    assertEquals(x.second, Finder(x.first.first, 32, x.first.second).best)
  }

  @Test
  internal fun part2() {
    val sim = Day19(Util.getInputAsList(19))
    assertEquals(4400, sim.qualityLevelsPart2())
  }


}


