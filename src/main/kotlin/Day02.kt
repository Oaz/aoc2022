
object Day02 {
  
  enum class GameResult {
    Win, Draw, Loss
  }
  
  enum class RPS {
    Rock {
      override fun winAgainst(): RPS = Scissors
      override fun loseAgainst(): RPS = Paper
    },
    Paper {
      override fun winAgainst(): RPS = Rock
      override fun loseAgainst(): RPS = Scissors
    },
    Scissors {
      override fun winAgainst(): RPS = Paper
      override fun loseAgainst(): RPS = Rock
    };
  
    abstract fun winAgainst(): RPS
    abstract fun loseAgainst(): RPS
    fun against(other: RPS): GameResult
    {
      if (other == winAgainst()) return GameResult.Win
      if (other == loseAgainst()) return GameResult.Loss
      return GameResult.Draw
    }
    fun chooseResponseForResult(wantedResult: GameResult): RPS {
      if (wantedResult == GameResult.Win) return loseAgainst()
      if (wantedResult == GameResult.Loss) return winAgainst()
      return this
    }
  }
  
  private val opponent = mapOf(
    'A' to RPS.Rock,
    'B' to RPS.Paper,
    'C' to RPS.Scissors,
  )
  private val responseScore = mapOf(
    RPS.Rock to 1,
    RPS.Paper to 2,
    RPS.Scissors to 3,
  )
  private val outcomeScore = mapOf(
    GameResult.Loss to 0,
    GameResult.Draw to 3,
    GameResult.Win to 6,
  )
  
  private val inPart1xyzIsResponse = mapOf(
    'X' to RPS.Rock,
    'Y' to RPS.Paper,
    'Z' to RPS.Scissors,
  )
  fun part1Score(gameDefinition: String): Int {
    val opponentChoice = opponent[gameDefinition[0]]!!
    val myResponse = inPart1xyzIsResponse[gameDefinition[2]]!!
    return responseScore[myResponse]!! + outcomeScore[myResponse.against(opponentChoice)]!!
  }
  
  private val inPart2xyzIsStrategy = mapOf(
    'X' to GameResult.Loss,
    'Y' to GameResult.Draw,
    'Z' to GameResult.Win,
  )
  fun part2Score(gameDefinition: String): Int {
    val opponentChoice = opponent[gameDefinition[0]]!!
    val wantedResult = inPart2xyzIsStrategy[gameDefinition[2]]!!
    val myResponse = opponentChoice.chooseResponseForResult(wantedResult)
    return responseScore[myResponse]!! + outcomeScore[wantedResult]!!
  }
  
}
