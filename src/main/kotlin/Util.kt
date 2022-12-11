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
}

fun MatchResult?.at(index: Int) = this?.groups?.get(index)?.value!!


