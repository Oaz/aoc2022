import kotlin.math.sign

class Day20(input: List<String>, decryptionKey: Long = 1) {
  val rules = Rules(input.map { it.toLong() }, decryptionKey)
  
  fun sumOfGroveCoordinates(repeat: Int = 1) =
    generateSequence(Mix(rules)) { it.next() }.elementAt(rules.size * repeat).sumOfGroveCoordinates()
  
  class Rules(numbers: List<Long>, decryptionKey: Long) {
    private val circleShift = numbers.size - 1
    private val toPositive = -(((numbers.minOrNull()!! / circleShift) - 1)) * circleShift
    val rules = numbers.map { it * decryptionKey }.map { Rule(it, ((it + toPositive) % circleShift).toInt()) }
    val size = rules.size
    val zero = rules.indexOfFirst { it.number == 0L }
    val indices = rules.indices.toList().toIntArray()
    operator fun get(index: Int) = rules[index]
  }
  
  class Rule(val number: Long, val delta: Int)
  
  class Mix(
    private val ruleOfPositions: IntArray, private val positionOfRules: IntArray,
    private val currentRuleIndex: Int, private val rules: Rules
  ) {
    constructor(rules: Rules) : this(rules.indices, rules.indices, 0, rules)
    
    val values by lazy { ruleOfPositions.map { rules[it].number } }
    private val circleShift by lazy { rules.size - 1 }
    private fun m(i: Int) = (i + circleShift) % circleShift
    val zero by lazy { positionOfRules[rules.zero] }
    private fun numberAt(i: Int) = rules[ruleOfPositions[(zero + i) % rules.size]].number
    fun sumOfGroveCoordinates() = listOf(1, 2, 3).sumOf { numberAt(it * 1000) }
    
    fun next(): Mix {
      val rule = rules[currentRuleIndex]
      val nextRuleIndex = (currentRuleIndex + 1) % rules.size
      if (rule.number == 0L)
        return Mix(ruleOfPositions, positionOfRules, nextRuleIndex, rules)
      val nextRuleOfPositions = ruleOfPositions.clone()
      val nextPositionOfRules = positionOfRules.clone()
      val currentPositionOfRule = positionOfRules[currentRuleIndex]
      val nextPositionOfRule = m(currentPositionOfRule + rule.delta)
      nextRuleOfPositions[nextPositionOfRule] = currentRuleIndex
      nextPositionOfRules[currentRuleIndex] = nextPositionOfRule
      val direction = (nextPositionOfRule - currentPositionOfRule).sign
      val rangeToShift =
        IntProgression.fromClosedRange(currentPositionOfRule, m(nextPositionOfRule - direction), direction)
      for (position in rangeToShift) {
        val newRuleAtPosition = ruleOfPositions[position + direction]
        nextRuleOfPositions[position] = newRuleAtPosition
        nextPositionOfRules[newRuleAtPosition] = position
      }
      return Mix(nextRuleOfPositions, nextPositionOfRules, nextRuleIndex, rules)
    }
  }
  
}