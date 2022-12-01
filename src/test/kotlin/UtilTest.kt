import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UtilTest {

    @Test
    fun fromResources() {
        Assertions.assertEquals(listOf("foo","bar","qix"), Util.fromResources("SomeFile.txt").readLines())
    }
}