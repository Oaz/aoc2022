

object Day01 {
  fun caloriesForElfCarryingMostCalories(input: Iterable<String>): Int {
    return computeCaloriesForEachElf(input).max()
  }
  
  fun caloriesForTop3ElfCarryingMostCalories(input: Iterable<String>): Int {
    return computeCaloriesForEachElf(input).sortedDescending().take(3).sum()
  }
  
  private fun computeCaloriesForEachElf(input: Iterable<String>) : List<Int> {
    val (_, caloriesForEachElf) = input.fold(Pair(0, listOf<Int>()))
    { (currentCalories, caloriesByElf), read ->
      if (read.isEmpty()) Pair(0, caloriesByElf + currentCalories)
      else Pair(currentCalories + read.toInt(), caloriesByElf)
    }
    return caloriesForEachElf
  }
}