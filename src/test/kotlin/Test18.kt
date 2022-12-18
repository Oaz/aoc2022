import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal object Test18 {

  private val example = listOf(
    "2,2,2",
    "1,2,2",
    "3,2,2",
    "2,1,2",
    "2,3,2",
    "2,2,1",
    "2,2,3",
    "2,2,4",
    "2,2,6",
    "1,2,5",
    "3,2,5",
    "2,1,5",
    "2,3,5",
  )
  
  @Test
  internal fun `part 1 in example`() {
    val sim = Day18(example)
    assertEquals(64, sim.totalVisibleFaces())
  }
  
  @Test
  internal fun part1() {
    val sim = Day18(Util.getInputAsList(18))
    assertEquals(3412, sim.totalVisibleFaces())
  }

  @Test
  internal fun `part 2 in example`() {
    val sim = Day18(example)
    assertEquals(58, sim.exteriorFaces())
  }
  
  @Test
  internal fun part2() {
    val sim = Day18(Util.getInputAsList(18))
    assertEquals(2018, sim.exteriorFaces())
  }


}


