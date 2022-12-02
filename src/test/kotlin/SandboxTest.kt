import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*


class Sandbox {
    fun add(a: Int, b: Int): Int {
        return a+b
    }
}

internal object SandboxTest {
    @Test
    internal fun `sequential assertions`() {
        val x = Sandbox()
        Assertions.assertEquals(4, x.add(2,2))
        Assertions.assertEquals(5, x.add(2,3))
        Assertions.assertEquals(6, x.add(3,3))
    }

    @Test
    internal fun `parallel assertions`() {
        val x = Sandbox()
        Assertions.assertAll(
            Executable { Assertions.assertEquals(4, x.add(2,2)) },
            Executable { Assertions.assertEquals(5, x.add(2,3)) },
            Executable { Assertions.assertEquals(6, x.add(3,3)) }
        )
    }

    @TestFactory
    internal fun `test factory`() = listOf(
        DynamicTest.dynamicTest("2+2 = 4") { Assertions.assertEquals(4, Sandbox().add(2,2)) },
        DynamicTest.dynamicTest("2+3 = 5") { Assertions.assertEquals(5, Sandbox().add(2,3)) },
        DynamicTest.dynamicTest("3+3 = 9") { Assertions.assertEquals(6, Sandbox().add(3,3)) }
    )

    @TestFactory
    internal fun `from test data`() = testData
        .map { (a, b, expectedSum) ->
            DynamicTest.dynamicTest("$a + $b = $expectedSum") {
                Assertions.assertEquals(expectedSum, Sandbox().add(a,b))
            }
        }
    private val testData = listOf(
        Triple(2,2,4),
        Triple(2,3,5),
        Triple(3,3,6),
    )
    
    data class MyTestData(
        val a: Int,
        val b: Int,
        val expectedSum: Int
    )
    
    @JvmStatic
    private fun otherTestData() = listOf(
        MyTestData(2,2,4),
        MyTestData(2,3,5),
        MyTestData(3,3,6),
    )
    @ParameterizedTest
    @MethodSource("otherTestData")
    fun `from other test data - method source`(x : MyTestData) {
        Assertions.assertEquals(x.expectedSum, Sandbox().add(x.a,x.b))
    }

    @BeforeEach
    fun setUp() {
    }
}