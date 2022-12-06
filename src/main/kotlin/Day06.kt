
object Day06 {
  fun startOfPacket(input: String) = findMarker(input, 4)
  fun startOfMessage(input: String) = findMarker(input, 14)
  
  private fun findMarker(input: String, width: Int): Int = input
    .windowed(width)
    .indexOfFirst { it.toSet().count() == width } + width
}
