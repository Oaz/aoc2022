import Day19.Kind.*
import Day19.Vector.Companion.infinite
import Day19.Vector.Companion.zeroes

class Day19(input: List<String>) {
  
  private val blueprints = input.map { readBlueprint(it) }
  
  fun qualityLevelsPart1()= blueprints.sumOf { it.id * Finder(it, 24, ::strategy1).geodeNumber }
  fun qualityLevelsPart2()= blueprints.take(3).map { Finder(it, 32, ::strategy3).geodeNumber }.reduce { a, b -> a*b }
  
  class Finder(blueprint: Blueprint, duration: Int, strategy:(Snapshot)->List<Kind>) {
    private val start = Snapshot(blueprint, zeroes, Ore.unit, strategy, duration)
    val best by lazy { findBest() }
    val geodeNumber by lazy { best.second }
  
    private fun findBest(): Pair<List<Kind>, Int> {
      val finder = AStar.on(start)
        .withNeighborsDistance { it.follow() }
        .withHeuristic { it.heuristic }
        .build()
      val result = finder.search(start) { it.timeout }
      val path = result.path
      return path.map { it.robot } to path.last().material.geode
    }
  }
  
  companion object {
    fun readBlueprint(input: String) = Blueprint(regex.findAll(input).map { it.value.toInt() }.toList())
    private val regex = """\d+""".toRegex()
  
    fun strategy1(s:Snapshot) = when {
      s.material.clay > s.blueprint.cost(Obsidian).clay -> listOf(Obsidian, Geode)
      (s.robots.clay > 0) && (s.robots.ore > 2) -> listOf(Clay, Obsidian, Geode)
      else -> listOf(Ore, Clay, Obsidian, Geode)
    }
  
    fun strategy2(s:Snapshot) = when {
      s.material.clay > s.blueprint.cost(Obsidian).clay + 1 -> listOf(Obsidian, Geode)
      (s.robots.clay > 0) && (s.robots.ore > 1) -> listOf(Clay, Obsidian, Geode)
      else -> listOf(Ore, Clay, Obsidian, Geode)
    }
  
    fun strategy3(s:Snapshot) = when {
      s.material.clay > s.blueprint.cost(Obsidian).clay -> listOf(Obsidian, Geode)
      (s.robots.clay > 0) && (s.robots.ore > 3) -> listOf(Clay, Obsidian, Geode)
      else -> listOf(Ore, Clay, Obsidian, Geode)
    }
  }
  
  data class Snapshot(
    val blueprint: Blueprint,
    val material: Vector,
    val robots: Vector,
    val strategy:(Snapshot)->List<Kind> = ::strategy1,
    val duration: Int = Int.MAX_VALUE,
    val robot: Kind = Ore,
    val distance: Int = 0,
    val time: Int = 0,
    val timeout:Boolean = false,
  ) {
    private val remainingTime = duration - time
    private val endPrice = 100
    private val geodeBonus = endPrice-1*(material.geode+remainingTime*robots.geode)
    val heuristic = if(timeout) endPrice else geodeBonus
  
    private fun nextSnapshots() =
          if(remainingTime == 0)
            listOf(end())
          else
            strategy(this).mapNotNull { makeNext(it) }
              .filter { it.time <= duration }
              .ifEmpty { listOf(forward(remainingTime)) }
  
    fun follow()= nextSnapshots().map { it to it.distance }
    
    private fun makeNext(robotKind: Kind): Snapshot? {
      val cost = blueprint.cost(robotKind)
      val remainingCost = cost - material
      if (remainingCost.allNegativeOrZero())
        return copyNext(robotKind, remainingCost, 1)
      val timeToRobot = (remainingCost / robots).max()
      if (timeToRobot == infinite)
        return null
      return copyNext(robotKind, remainingCost, timeToRobot + 1)
    }
    
    private fun copyNext(robotKind: Kind, remainingCost: Vector, deltaTime: Int) = copy(
      material = robots * deltaTime - remainingCost,
      robots = robots + robotKind.unit,
      distance = deltaTime,
      robot = robotKind,
      time = time + deltaTime
    )
  
    private fun forward(deltaTime: Int) =
      copy(material = material + robots * deltaTime, distance = deltaTime, time = time + deltaTime)
    
    private fun end() =
      copy(timeout = true, distance = geodeBonus)
  
    fun next(robotKinds: List<Kind>): Snapshot? = if (robotKinds.isEmpty()) this else
      makeNext(robotKinds.first())?.next(robotKinds.drop(1))
  }
  
  enum class Kind(val unit: Vector) {
    Ore(Vector(1, 0, 0, 0)),
    Clay(Vector(0, 1, 0, 0)),
    Obsidian(Vector(0, 0, 1, 0)),
    Geode(Vector(0, 0, 0, 1))
  }
  
  data class Blueprint(val id:Int, val oreForOre:Int, val oreForClay:Int,
    val oreForObsidian:Int, val clayForObsidian:Int,
    val oreForGeode:Int, val obsidianForGeode:Int) {
    constructor(input: List<Int>) : this(input[0],input[1],input[2],input[3],input[4],input[5],input[6])
    fun cost(robotKind: Kind) = when (robotKind) {
      Ore -> Vector(oreForOre, 0, 0, 0)
      Clay -> Vector(oreForClay, 0, 0, 0)
      Obsidian -> Vector(oreForObsidian, clayForObsidian, 0, 0)
      Geode -> Vector(oreForGeode, 0, obsidianForGeode, 0)
    }
  }
  
  data class Vector(val ore: Int, val clay: Int, val obsidian: Int, val geode: Int) {
    private fun items() = listOf(ore, clay, obsidian, geode)
    fun allNegativeOrZero() = items().all { it <= 0 }
    fun max() = items().maxOrNull()!!
    operator fun plus(other: Vector) = Vector(
      ore + other.ore,
      clay + other.clay,
      obsidian + other.obsidian,
      geode + other.geode,
    )
    
    operator fun minus(other: Vector) = Vector(
      ore - other.ore,
      clay - other.clay,
      obsidian - other.obsidian,
      geode - other.geode,
    )
    
    operator fun div(other: Vector) = Vector(
      divQuantities(ore, other.ore),
      divQuantities(clay, other.clay),
      divQuantities(obsidian, other.obsidian),
      divQuantities(geode, other.geode),
    )
    
    operator fun times(k: Int): Vector = Vector(k * ore, k * clay, k * obsidian, k * geode)
    
    companion object {
      val zeroes = Vector(0, 0, 0, 0)
      const val infinite = Int.MAX_VALUE
      const val minfinite = Int.MIN_VALUE
      private fun divQuantities(a: Int, b: Int) = when {
        a == 0 -> 0
        b == 0 && a > 0 -> infinite
        b == 0 -> minfinite
        a % b == 0 -> a / b
        else -> 1 + a / b
      }
    }
  }
}