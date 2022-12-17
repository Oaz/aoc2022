import java.lang.Integer.max

class Day17(input: String) {
  
  val jets = input.trim().map {
    when (it) {
      '<' -> Direction.Left
      '>' -> Direction.Right
      else -> TODO()
    }
  }
  
  fun findHeight(count: Long): Long {
    val magicUpperLimitToFindPeriod = 2023
    val heights =
      generateSequence(Falling.appear(0, 0, emptyTower), ::land)
        .take(magicUpperLimitToFindPeriod).map { it.couple.tower.height }.toList()
    if (count < magicUpperLimitToFindPeriod)
      return heights[count.toInt()].toLong()
    val deltas = heights.zipWithNext().map { it.second - it.first }
    val (baseLength, period) = Util.findCycle(deltas)!!
    val periodData = deltas.drop(baseLength).take(period)
    val startAt = count - baseLength
    val remainder = periodData.take((startAt % period).toInt()).sum()
    return heights[baseLength] + (startAt / period) * periodData.sum() + remainder
  }
  
  fun land(falling: Falling) =
    generateSequence(falling) { it.pushAndFall(jets) }.dropWhile { it.shapeIndex == falling.shapeIndex }.first()
  
  data class Falling(val couple: Couple, val shapeIndex: Int, val jetIndex: Int) {
    
    fun pushAndFall(jets: List<Direction>) =
      pushAndFall(jets[jetIndex]).copy(jetIndex = (jetIndex + 1) % jets.size)
    
    fun pushAndFall(direction: Direction): Falling {
      val afterPush = couple.push(direction)
      val newCouple = afterPush.down()
      return if (newCouple.clash())
        appear((shapeIndex + 1) % shapes.size, jetIndex, afterPush.merge())
      else
        Falling(newCouple, shapeIndex, jetIndex)
    }
    
    companion object {
      fun appear(shapeIndex: Int, jetIndex: Int, tower: Zone) =
        shapes[shapeIndex].let {
          Falling(Couple(it, it.height + 3, tower), shapeIndex, jetIndex)
        }
    }
  }
  
  data class Couple(val rock: Zone, val elevation: Int, val tower: Zone) {
    fun push(direction: Direction) = copy(rock = rock.push(direction)).let { if (it.clash()) this else it }
    fun down() = copy(elevation = elevation - 1)
    fun clash() =
      rock.rows.takeLast(max(0, rock.height - elevation)).let { rockBottom ->
        if (rockBottom.isEmpty())
          false
        else tower.rows.drop(max(0, -elevation)).take(rockBottom.size).let { towerTop ->
          if (towerTop.size < rockBottom.size) true
          else rockBottom.zip(towerTop).any { it.first.clashesWith(it.second) }
        }
      }
    
    fun merge() =
      Zone((-max(0, elevation) until tower.height).map {
        when {
          it < 0 -> rock.rows[it + elevation]
          it >= rock.height - elevation || it < -elevation -> tower.rows[it]
          else -> rock.rows[it + elevation].mergeWith(tower.rows[it])
        }
      })
    
    private fun String.clashesWith(other: String) =
      zip(other).any { it.first == '#' && it.second == '#' }
    
    private fun String.mergeWith(other: String): String =
      zip(other).map { if (it.first == '#' || it.second == '#') '#' else '.' }.joinToString("")
  }
  
  data class Zone(val rows: List<String>) {
    val height = rows.size
    val draw by lazy { rows.joinToString("\n") }
    
    fun push(direction: Direction) =
      when (direction) {
        Direction.Left ->
          if (rows.any { it.first() == '#' }) this
          else Zone(rows.map { it.drop(1) + '.' })
        
        Direction.Right ->
          if (rows.any { it.last() == '#' }) this
          else Zone(rows.map { '.' + it.dropLast(1) })
      }
    
    companion object {
      fun make(vararg rows: String) = Zone(rows.toList())
    }
  }
  
  enum class Direction {
    Left, Right
  }
  
  companion object {
    val emptyTower = Zone.make()
    
    val shapes = listOf(
      Zone.make(
        "..####.",
      ),
      Zone.make(
        "...#...",
        "..###..",
        "...#...",
      ),
      Zone.make(
        "....#..",
        "....#..",
        "..###..",
      ),
      Zone.make(
        "..#....",
        "..#....",
        "..#....",
        "..#....",
      ),
      Zone.make(
        "..##...",
        "..##...",
      ),
    )
  }
}