import java.io.File

object Util {
  fun getInputAsString(day: Int): String {
    return fromResources(day).readText()
  }
  
  fun getInputAsList(day: Int): List<String> {
    return fromResources(day).readLines()
  }
  
  private fun fromResources(day: Int): File {
    return fromResources("input%02d.txt".format(day))
  }
  
  fun fromResources(name: String): File {
    return File(javaClass.classLoader.getResource(name).toURI())
  }
  
  fun scale(k: Int, input: List<String>): List<String> {
    val repeatRows = input.map { it.repeat(k) }
    return (1..k).flatMap { repeatRows }
  }
  
  fun <T> findCycle(data: List<T>): Pair<Int, Int>? {
    val candidates = generateSequence(data to 0) {
      it.first.drop(1) to it.second + 1
    }
    val result = candidates.map { (data, startAt) ->
      val z = zArray(data)
      val zMax: Int = z.maxOrNull()!!
      val period = z.indexOf(zMax)
      if (zMax + period == z.size)
        (startAt to period)
      else
        null
    }.filterNotNull().firstOrNull()
    return result
  }
  
  fun <T> zArray(s: List<T>): IntArray {
    val z = IntArray(s.size)
    var left = 0
    var right = 0
    for (i in 1 until s.size) {
      if (i > right) {
        left = i
        right = i
        while (right < s.size && s[right] == s[right - left]) {
          right++
        }
        z[i] = right - left
        right--
      } else {
        val k = i - left
        if (z[k] < right - i + 1) {
          z[i] = z[k]
        } else {
          left = i
          while (right < s.size && s[right] == s[right - left]) {
            right++
          }
          z[i] = right - left
          right--
        }
      }
    }
    return z
  }
}

fun MatchResult?.at(index: Int) = this?.groups?.get(index)?.value!!


