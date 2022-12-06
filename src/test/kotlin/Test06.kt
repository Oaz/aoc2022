import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test06 {
  
  data class ExpectedMarkers(
    val input: String,
    val expectedStartOfPacket: Int,
    val expectedStartOfMessage: Int,
  )
  
  @JvmStatic
  private fun example() = listOf(
    ExpectedMarkers("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19),
    ExpectedMarkers("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23),
    ExpectedMarkers("nppdvjthqldpwncqszvftbrmjlhg", 6, 23),
    ExpectedMarkers("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29),
    ExpectedMarkers("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26),
  )
  
  @ParameterizedTest
  @MethodSource("example")
  internal fun `find start of packet`(x: ExpectedMarkers) {
    assertEquals(x.expectedStartOfPacket, Day06.startOfPacket(x.input))
  }
  
  @ParameterizedTest
  @MethodSource("example")
  internal fun `find start of message`(x: ExpectedMarkers) {
    assertEquals(x.expectedStartOfMessage, Day06.startOfMessage(x.input))
  }
  
  @Test
  internal fun part1() {
    assertEquals(1987, Day06.startOfPacket(Util.getInputAsString(6)))
  }
  
  @Test
  internal fun part2() {
    assertEquals(3059, Day06.startOfMessage(Util.getInputAsString(6)))
  }
}


