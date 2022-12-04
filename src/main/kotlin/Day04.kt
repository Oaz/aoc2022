object Day04 {
  
  data class AssignmentPair(
    val left: IntRange,
    val right: IntRange
  )
  
  fun createAssignmentPair(input: String): AssignmentPair {
    val n = regex.matchEntire(input)
    return AssignmentPair(n.at(1)..n.at(2), n.at(3)..n.at(4))
  }
  private val regex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
  private fun MatchResult?.at(index: Int): Int = this?.groups?.get(index)?.value?.toInt()!!
  
  fun parseAndCount(input: Iterable<String>, counter: (AssignmentPair) -> Boolean): Int =
    input.count { counter(createAssignmentPair(it)) }
  
  fun AssignmentPair.oneRangeFullyContainTheOther(): Boolean =
    left.fullyContains(right) || right.fullyContains(left)
  
  fun AssignmentPair.rangesOverlap(): Boolean =
    left.containsBoundOf(right) || right.containsBoundOf(left)
  
  private fun IntRange.fullyContains(other: IntRange): Boolean =
    this.first <= other.first && other.last <= this.last
  
  private fun IntRange.containsBoundOf(other: IntRange): Boolean =
    this.contains(other.first) || this.contains(other.last)
  
}

