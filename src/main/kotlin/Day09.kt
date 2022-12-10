import java.lang.Exception
import kotlin.math.sqrt

class Day09(input: List<String>) {
  
  data class XY(val x: Int, val y: Int) {
    operator fun plus(other: XY): XY = XY(x + other.x, y + other.y)
    operator fun minus(other: XY): XY = XY(x - other.x, y - other.y)
    operator fun div(scalar:Int): XY = XY(x/scalar, y/scalar)
    fun norm(): Double = sqrt((x*x + y*y).toDouble())
    fun isVerticalOrHorizontal(): Boolean = x == 0 || y == 0
  }
  
  data class Physics(val head:XY, val tail:XY) {
    fun moveHead(move:XY) : Physics {
      val newHead = head + move
      val tailToNewHead = newHead - tail
      return Physics(newHead,
        if (tailToNewHead.norm() < 2) tail
        else if(move.isVerticalOrHorizontal()) head
        else if(tailToNewHead.isVerticalOrHorizontal()) tail + tailToNewHead / 2
        else tail + move
      )
    }
  }
  
  data class Motion(val direction: Direction, val steps: Int) {
    fun moves() : List<XY> = (1..steps).map { direction.vector() }
  }
  val motion = input.map { Motion(Direction.read(it[0]), it.substring(2).toInt()) }
  private val moves = motion.flatMap { it.moves() }
  
  fun shortRopes()= moves.scan(Physics(XY(0,0), XY(0,0))) { rope, move -> rope.moveHead(move) }
  fun uniqueShortRopeTailPositions() = shortRopes().map { it.tail }.distinct().count()
  
  data class LongRope(val knots: List<XY>) {
    constructor(head:XY) : this((1..10).map { head })
    fun moveHead(move: XY) : LongRope {
      val knotPairs = knots.zip(knots.drop(1)).map { Physics(it.first, it.second) }
      val newKnots = knotPairs
        .scan(knots.first()+move) { newKnot, previousKnotPair ->
          val inducedMove = newKnot - previousKnotPair.head
          val change = previousKnotPair.moveHead(inducedMove)
          change.tail
        }
      return LongRope(newKnots)
    }
  }
  private fun longRopes() = moves.scan(LongRope(XY(0,0))) { rope, move -> rope.moveHead(move) }
  fun uniqueLongRopeTailPositions() = longRopes().map { it.knots[9] }.distinct().count()
  
  enum class Direction {
    Up { override fun vector() = XY(0,1) },
    Down { override fun vector() = XY(0,-1) },
    Left { override fun vector() = XY(-1,0)  },
    Right { override fun vector() = XY(1,0) };
    abstract fun vector():XY
    companion object {
      fun read(c : Char) : Direction = when(c) {
        'U' -> Up
        'D' -> Down
        'L' -> Left
        'R' -> Right
        else -> throw Exception("unexpected move")
      }
    }
  }
}