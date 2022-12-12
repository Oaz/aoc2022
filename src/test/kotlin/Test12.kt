import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.lessThanOrEqualTo

internal object Test12 {
  
  private val example = listOf(
    "Sabqponm",
    "abcryxxl",
    "accszExk",
    "acctuvwj",
    "abdefghi",
  )
  
  @Test
  internal fun `part 1 in example`() {
    val result = Day12(example).fewestStepsToEnd()
    assertThat(result.length, `is`(equalTo(31)))
    assertThat(result.visited, `is`(lessThanOrEqualTo(40)))
  }
  
  @Test
  internal fun part1() {
    val result = Day12(Util.getInputAsList(12)).fewestStepsToEnd()
    assertThat(result.length, `is`(equalTo(383)))
    assertThat(result.visited, `is`(lessThanOrEqualTo(2714)))
  }
  
  @Test
  internal fun `part 2 in example`() {
    val sim = Day12(example)
    assertEquals(29, sim.fewestStepsToMultipleStartChoices().length)
  }
  
  @Test
  internal fun part2() {
    val sim = Day12(Util.getInputAsList(12))
    assertEquals(377, sim.fewestStepsToMultipleStartChoices().length)
  }

}


