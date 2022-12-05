object Day05 {
  
  data class Crate(val tag: Char)
  data class Move(val count: Int, val from: Int, val to: Int)
  
  abstract class Ship(var stacks: ArrayList<List<Crate>>) {
    fun signature(): String = String(stacks.map { it.first().tag }.toCharArray())
    
    fun execute(moves: List<Move>): Ship {
      for (move in moves) this.execute(move)
      return this
    }
    
    fun push(stackNumber: Int, crates: List<Crate>) {
      stacks[stackNumber - 1] = crates + stacks[stackNumber - 1]
    }
    
    fun pop(stackNumber: Int, crateCount: Int): List<Crate> {
      val crates = stacks[stackNumber - 1].take(crateCount)
      stacks[stackNumber - 1] = stacks[stackNumber - 1].drop(crateCount)
      return crates
    }
    
    abstract fun execute(move: Move): Ship
  }
  
  class ShipWithCrateMover9000(stacks: ArrayList<List<Crate>>) : Ship(stacks) {
    override fun execute(move: Move): Ship {
      val crates = pop(move.from, move.count).reversed()
      push(move.to, crates)
      return this
    }
  }
  
  class ShipWithCrateMover9001(stacks: ArrayList<List<Crate>>) : Ship(stacks) {
    override fun execute(move: Move): Ship {
      val crates = pop(move.from, move.count)
      push(move.to, crates)
      return this
    }
  }
  
  fun read(input: List<String>): Pair<ArrayList<List<Crate>>, List<Move>> {
    val crateRows = input.takeWhile(String::isNotEmpty).dropLast(1).map(::getOnlyUsefulCharInCrateRow)
    val numberOfStacks = crateRows.maxOf { it.length }
    val emptyStacks = ArrayList(List(numberOfStacks) { listOf<Crate>() })
    val crates = crateRows.fold(emptyStacks) { stacks, row ->
      for ((index, tag) in row.withIndex()) if (tag != ' ')
        stacks[index] = stacks[index] + Crate(tag)
      stacks
    }
    val moves = input.dropWhile(String::isNotEmpty).drop(1).map(::parseMove)
    return Pair(crates, moves)
  }
  
  fun getOnlyUsefulCharInCrateRow(input : String) : String =
    input.filterIndexed { index, _ -> (index - 1) % 4 == 0 }
  
  private fun parseMove(input: String): Move {
    val match = moveRegex.matchEntire(input)
    return Move(match.at(1), match.at(2), match.at(3))
  }
  
  private val moveRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()
  private fun MatchResult?.at(index: Int): Int = this?.groups?.get(index)?.value?.toInt()!!
}


