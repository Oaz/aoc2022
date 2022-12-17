import Day15.HorizontalSegment
import Day15.XY
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test15 {
  
  @JvmStatic
  private fun sensorReading() = listOf(
    "Sensor at x=2, y=18: closest beacon is at x=2, y=15" to (XY(2,18) to XY(2,15)),
    "Sensor at x=2, y=18: closest beacon is at x=-2, y=15" to (XY(2,18) to XY(-2,15)),
  )
  @ParameterizedTest
  @MethodSource("sensorReading")
  internal fun `read sensors`(x: Pair<String, Pair<XY,XY>>) {
    assertEquals(x.second, Day15.readSensor(x.first))
  }
  
  @JvmStatic
  private fun intersections() = listOf(
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to -3) to null,
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to -2) to HorizontalSegment(XY(8,-2), XY(8,-2)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to -1) to HorizontalSegment(XY(7,-1), XY(9,-1)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 1) to HorizontalSegment(XY(5,1), XY(11,1)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 2) to HorizontalSegment(XY(4,2), XY(12,2)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 6) to HorizontalSegment(XY(0,6), XY(16,6)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 7) to HorizontalSegment(XY(-1,7), XY(17,7)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 8) to HorizontalSegment(XY(0,8), XY(16,8)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 10) to HorizontalSegment(XY(3,10), XY(14,10)),
    ("Sensor at x=8, y=7: closest beacon is at x=14, y=10" to 10) to HorizontalSegment(XY(2,10), XY(13,10)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 16) to HorizontalSegment(XY(8,16), XY(8,16)),
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 17) to null,
    ("Sensor at x=8, y=7: closest beacon is at x=2, y=10" to 18) to null,
    ("Sensor at x=8, y=7: closest beacon is at x=8, y=7" to 18) to null,
    ("Sensor at x=8, y=7: closest beacon is at x=8, y=8" to 7) to HorizontalSegment(XY(7,7), XY(9,7)),
    ("Sensor at x=8, y=7: closest beacon is at x=8, y=8" to 8) to null,
  )
  @ParameterizedTest
  @MethodSource("intersections")
  internal fun `compute intersections`(x: Pair<Pair<String,Int>, HorizontalSegment>) {
    val (sensor, beacon) = Day15.readSensor(x.first.first)
    assertEquals(x.second, Day15.makeNBZ(sensor,beacon).intersectRowExcludeBeacon(x.first.second))
  }
  
  @JvmStatic
  private fun part1examples() = listOf(
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to -3) to 0,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to -2) to 1,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to -1) to 3,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 1) to 7,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 2) to 9,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 6) to 17,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 7) to 19,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 8) to 17,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 16) to 1,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 17) to 0,
    (listOf("Sensor at x=8, y=7: closest beacon is at x=2, y=10") to 18) to 0,
    (listOf(
      "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
      "Sensor at x=9, y=16: closest beacon is at x=11, y=14",
    ) to 10) to 12,
    (listOf(
      "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
      "Sensor at x=9, y=16: closest beacon is at x=11, y=14",
    ) to 13) to 7,
    (listOf(
      "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
      "Sensor at x=9, y=16: closest beacon is at x=11, y=14",
    ) to 14) to 5,
    (listOf(
      "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
      "Sensor at x=9, y=16: closest beacon is at x=11, y=14",
    ) to 15) to 7,
    (listOf(
      "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
      "Sensor at x=9, y=16: closest beacon is at x=11, y=14",
    ) to 17) to 7,
  )
  @ParameterizedTest
  @MethodSource("part1examples")
  internal fun `compute part1 examples`(x: Pair<Pair<List<String>,Int>, Int>) {
    val sim = Day15(x.first.first)
    assertEquals(x.second, sim.positionsWithNoBeaconInRow(x.first.second))
  }
  
  @Test
  internal fun part1() {
    val sim = Day15(Util.getInputAsList(15))
    assertEquals(4961647, sim.positionsWithNoBeaconInRow(2000000))
  }
  
  private val example = listOf(
    "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
    "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
    "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
    "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
    "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
    "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
    "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
    "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
    "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
    "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
    "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
    "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
    "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
    "Sensor at x=20, y=1: closest beacon is at x=15, y=3",
  )
  
  @Test
  internal fun `part 2 in example`() {
    val sim = Day15(example)
    assertEquals(56000011, sim.tuningFrequency(20))
  }

  @Test
  internal fun part2() {
    val sim = Day15(Util.getInputAsList(15))
    assertEquals(12274327017867, sim.tuningFrequency(4000000))
  }

}


