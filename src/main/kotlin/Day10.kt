import java.lang.Exception

class Day10(input: List<String>) {
  
  data class Instruction(private val input: String) {
    private val code = input.substring(0,4)
    val deltas = when(code) {
      "noop" -> listOf(0)
      "addx" -> listOf(0,input.substring(5).toInt())
      else -> { throw Exception("Unexpected instruction") }
    }
  }
  val deltas = input.flatMap { Instruction(it).deltas }
  val xs = deltas.scan(1) { x,delta -> x+delta }

  val strengths = xs.mapIndexed { cycle, x -> (cycle+1)*x }
  private val cycles = listOf(20, 60, 100, 140, 180, 220)
  val sumOfStrengths = strengths.filterIndexed { cycle, _ -> cycles.contains(cycle+1) }.sum()
  
  val screen = xs.chunked(40).map { drawLine(it) }.take(6)
  
  private fun drawLine(spritePositions: List<Int>) =
    String(spritePositions.mapIndexed { pixelPosition, spritePosition -> pixelFor(pixelPosition,spritePosition) }.toCharArray())
  
  private fun pixelFor(pixelPosition: Int, spritePosition: Int) =
    if(pixelPosition in (spritePosition-1 .. spritePosition+1)) '#' else '.'
  
}