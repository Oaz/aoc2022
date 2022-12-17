import java.lang.Exception
import java.lang.Integer.max
import kotlin.math.absoluteValue

class Day15(input: List<String>) {
  
  private val readings = input.map(::readSensor)
  private val noBeaconZones = readings.map { makeNBZ(it.first, it.second) }
  
  fun positionsWithNoBeaconInRow(row: Int) =
    noBeaconZones.mapNotNull { it.intersectRowExcludeBeacon(row) }.merge().sumOf { it.length() }
  
  fun tuningFrequency(maxCoordinate: Int): Long {
    val frequencyBase = 4000000
    for(y in 0..maxCoordinate) {
      val hole = noBeaconZones.mapNotNull { it.intersectRow(y) }.findHole()
      if(hole != null && hole <= frequencyBase)
        return hole.toLong() * frequencyBase + y
    }
    throw Exception("Cannot find beacon position")
  }
  
  private fun List<HorizontalSegment>.findHole(): Int? {
    val sorted = sortedBy { it.start.x }
    return sorted.scan(sorted.first().end.x to false) { (rightest,_), segment ->
      if (segment.start.x > rightest)
        (rightest+1) to true
      else
        max(rightest,segment.end.x) to false
    }.firstOrNull { it.second }?.first
  }
  
  private fun List<HorizontalSegment>.merge(): List<HorizontalSegment> =
    sortedBy { it.start.x }.fold(listOf()) { group, segment ->
      if (group.isEmpty())
        listOf(segment)
      else
        group.dropLast(1) + group.last().mergeRight(segment)
    }
  
  data class HorizontalSegment(val start: XY, val end: XY) {
    fun length() = end.x - start.x + 1
    fun mergeRight(other:HorizontalSegment) =
      if(end.x < other.start.x)
        listOf(this, other)
      else if(other.end.x <= end.x )
        listOf(this)
      else listOf(HorizontalSegment(start,other.end))
  }
  
  data class NBZ(val center: XY, val beacon:XY, val radius: Int) {
    fun intersectRow(row:Int) : HorizontalSegment? {
      val halfSize = radius - (XY(center.x,row)-center).manhattan()
      return if(halfSize < 0)
        null
      else
        HorizontalSegment(XY(center.x-halfSize,row), XY(center.x+halfSize,row))
    }
    fun intersectRowExcludeBeacon(row:Int) : HorizontalSegment? {
      val halfSize = radius - (XY(center.x,row)-center).manhattan()
      return if(halfSize < 0)
        null
      else if(row == beacon.y) {
        if(beacon.x == center.x)
          null
        else if(beacon.x < center.x)
          HorizontalSegment(XY(center.x-halfSize+1,row), XY(center.x+halfSize,row))
        else
          HorizontalSegment(XY(center.x-halfSize,row), XY(center.x+halfSize-1,row))
      } else
        HorizontalSegment(XY(center.x-halfSize,row), XY(center.x+halfSize,row))
    }
  }
  
  data class XY(val x: Int, val y: Int) {
    operator fun plus(other: XY) = XY(x + other.x, y + other.y)
    operator fun minus(other: XY) = XY(x - other.x, y - other.y)
    fun manhattan() = x.absoluteValue + y.absoluteValue
  }
  
  companion object {
    fun readSensor(input: String): Pair<XY, XY> {
      val n = regex.findAll(input).map { it.value.toInt() }.toList()
      return XY(n[0], n[1]) to XY(n[2], n[3])
    }
    fun makeNBZ(center : XY, beacon:XY): NBZ {
      return NBZ(center, beacon, (beacon-center).manhattan())
    }
    private val regex = """[-\d]+""".toRegex()
  }
  
}

