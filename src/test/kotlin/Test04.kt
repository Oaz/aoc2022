import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import Day04.AssignmentPair
import Day04.parseAndCount
import Day04.rangesOverlap
import Day04.oneRangeFullyContainTheOther

internal object Test04 {
  
  
  data class InputParsing(
    val input: String,
    val assignment: AssignmentPair,
    val expectedContainment: Boolean,
    val expectedOverlap: Boolean
  )
  
  @JvmStatic
  private fun example() = listOf(
    InputParsing("2-4,6-8", AssignmentPair(2..4, 6..8), false, false),
    InputParsing("2-3,4-5", AssignmentPair(2..3, 4..5), false, false),
    InputParsing("5-7,7-9", AssignmentPair(5..7, 7..9), false, true),
    InputParsing("2-8,3-7", AssignmentPair(2..8, 3..7), true, true),
    InputParsing("6-6,4-6", AssignmentPair(6..6, 4..6), true, true),
    InputParsing("2-6,4-8", AssignmentPair(2..6, 4..8), false, true),
  )
  
  @ParameterizedTest
  @MethodSource("example")
  internal fun `input parsing`(x: InputParsing) {
    assertEquals(x.assignment, Day04.createAssignmentPair(x.input))
  }
  
  @ParameterizedTest
  @MethodSource("example")
  internal fun `check containment`(x: InputParsing) {
    assertEquals(x.expectedContainment, x.assignment.oneRangeFullyContainTheOther())
  }
  
  @ParameterizedTest
  @MethodSource("example")
  internal fun `check overlap`(x: InputParsing) {
    assertEquals(x.expectedOverlap, x.assignment.rangesOverlap())
  }
  
  @Test
  internal fun `example for part 1`() {
    assertEquals(2, parseAndCount(example().map { it.input }) { it.oneRangeFullyContainTheOther() })
  }
  
  @Test
  internal fun `part 1`() {
    assertEquals(562, parseAndCount(Util.getInputAsList(4)) { it.oneRangeFullyContainTheOther() })
  }
  
  @Test
  internal fun `example for part 2`() {
    assertEquals(4, parseAndCount(example().map { it.input }) { it.rangesOverlap() })
  }
  
  @Test
  internal fun `part 2`() {
    assertEquals(924, parseAndCount(Util.getInputAsList(4)) { it.rangesOverlap() })
  }
  
}


