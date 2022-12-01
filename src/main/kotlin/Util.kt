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
}

