import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import Day02.RPS as RPS
import Day02.GameResult as Result

internal object Test02 {
  
  data class RPSRule(
    val player1: RPS,
    val player2: RPS,
    val resultForPlayer1: Result
  )

  @JvmStatic
  private fun rockPaperScissorsRules() = listOf(
    RPSRule(RPS.Rock, RPS.Rock, Result.Draw),
    RPSRule(RPS.Rock, RPS.Paper, Result.Loss),
    RPSRule(RPS.Rock, RPS.Scissors, Result.Win),
    RPSRule(RPS.Paper, RPS.Rock, Result.Win),
    RPSRule(RPS.Paper, RPS.Paper, Result.Draw),
    RPSRule(RPS.Paper, RPS.Scissors, Result.Loss),
    RPSRule(RPS.Scissors, RPS.Rock, Result.Loss),
    RPSRule(RPS.Scissors, RPS.Paper, Result.Win),
    RPSRule(RPS.Scissors, RPS.Scissors, Result.Draw),
  )
  @ParameterizedTest
  @MethodSource("rockPaperScissorsRules")
  internal fun `A0 - Rock-Paper-Scissors rules`(rule : RPSRule)
  {
    assertEquals(rule.resultForPlayer1, rule.player1.against(rule.player2))
    assertEquals(rule.player1, rule.player2.chooseResponseForResult(rule.resultForPlayer1))
  }
  
  private val example = listOf(
    "A Y",
    "B X",
    "C Z",
  )
  
  data class Score(
    val gameDefinition: String,
    val expectedScore: Int
  )
  
  @JvmStatic
  private fun expectedPart1Scores() = listOf(
    Score("A Y", 8),
    Score("B X", 1),
    Score("C Z", 6),
  )
  @ParameterizedTest
  @MethodSource("expectedPart1Scores")
  fun `B0 - compute part 1 score`(x : Score) {
    assertEquals(x.expectedScore, Day02.part1Score(x.gameDefinition))
  }
  @Test
  internal fun `B1 - score on example for part 1`() {
    assertEquals(15, example.sumOf(Day02::part1Score))
  }
  @Test
  internal fun `B2 - part 1`() {
    assertEquals(9651, Util.getInputAsList(2).sumOf(Day02::part1Score))
  }
  
  @JvmStatic
  private fun expectedPart2Scores() = listOf(
    Score("A Y", 4),
    Score("B X", 1),
    Score("C Z", 7),
  )
  @ParameterizedTest
  @MethodSource("expectedPart2Scores")
  fun `C0 - compute part 2 score`(x : Score) {
    assertEquals(x.expectedScore, Day02.part2Score(x.gameDefinition))
  }
  @Test
  internal fun `C1 - score on example for part 2`() {
    assertEquals(12, example.sumOf(Day02::part2Score))
  }
  @Test
  internal fun `C2 - part 2`() {
    assertEquals(10560, Util.getInputAsList(2).sumOf(Day02::part2Score))
  }
  
  
}


