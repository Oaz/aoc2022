import java.lang.Exception

open class Day11(input: List<String>, private val relief: Long, private val allRounds: Int) {
  
  companion object {
    fun readOperation(input: String): (Long) -> Long =
      if (input == "old * old") { x -> x * x }
      else {
        val param = input.substring(6).toLong()
        when (input.substring(4, 5)) {
          "+" -> { x -> x + param }
          "*" -> { x -> x * param }
          else -> throw Exception("Unknown operation: $input")
        }
      }
  }
  
  class Monkey(input: List<String>) {
    private val id = input[0].substring(7, 8).toInt()
    private val startingItems = input[1].substring(18).split(", ").map { it.toLong() }
    val initialInspects = startingItems.map { MonkeyInspect(id, it) }
    private val operation = readOperation(input[2].substring(19))
    val divisibility = input[3].substring(21).toLong()
    private val ifTrue = input[4].substring(29).toInt()
    private val ifFalse = input[5].substring(30).toInt()
    fun inspect(worryLevel: Long, relief: Long, modulo: Long): MonkeyInspect {
      val newLevel = (operation(worryLevel) / relief) % modulo
      return MonkeyInspect(if (newLevel % divisibility == 0L) ifTrue else ifFalse, newLevel)
    }
  }
  
  data class MonkeyInspect(val monkeyId: Int, val worryLevel: Long)
  
  val monkeys = input.chunked(7).map { Monkey(it) }
  private val modulo = monkeys.fold(1L) { factor, monkey -> factor * monkey.divisibility }
  val initialInspects = monkeys.flatMap { it.initialInspects }
  fun proceed(inspect: MonkeyInspect) = monkeys[inspect.monkeyId].inspect(inspect.worryLevel, relief, modulo)
  
  data class Focus(val monkeyId: Int, val worryLevels: List<MonkeyInspect>, val activity: Long) {
    fun add(levels: List<MonkeyInspect>) = Focus(monkeyId, worryLevels + levels, activity)
    fun allThrown() = Focus(monkeyId, listOf(), activity + worryLevels.count())
  }
  
  private fun monkeyPlay(focuses: List<Focus>): List<Focus> {
    val focus = focuses.first()
    val throws = focus.worryLevels.map { proceed(it) }.groupBy { it.monkeyId }
    val catches = focuses.drop(1).map { it.add(throws.getOrDefault(it.monkeyId, listOf())) }
    return catches + focus.allThrown()
  }
  
  fun proceed(focuses: List<Focus>) = monkeys.fold(focuses) { hs, _ -> monkeyPlay(hs) }
  
  val initialFocuses = initialInspects.groupBy { it.monkeyId }.map { Focus(it.key, it.value, 0) }
  fun play(rounds: Int) = (1..rounds).fold(initialFocuses) { holdings, _ -> proceed(holdings) }
  fun playAll() = play(allRounds)
}

fun List<Day11.Focus>.monkeyBusiness()= this.map { it.activity }.sortedDescending().take(2).reduce { a1, a2 -> a1 * a2 }

class Day11Part1(input:List<String>) : Day11(input,3,20)
class Day11Part2(input:List<String>) : Day11(input,1,10000)
