
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


internal class Test01 {

    private val sample = listOf(
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000",
        ""
    )
    
    @Test
    internal fun `top 1 on sample`() {
        assertEquals(24000, Day01.caloriesForElfCarryingMostCalories(sample))
    }
    
    @Test
    internal fun `part 1`() {
        assertEquals(69289, Day01.caloriesForElfCarryingMostCalories(Util.getInputAsList(1)))
    }
    
    @Test
    internal fun `top 3 on sample`() {
        assertEquals(45000, Day01.caloriesForTop3ElfCarryingMostCalories(sample))
    }
    
    @Test
    internal fun `part 2`() {
        assertEquals(205615, Day01.caloriesForTop3ElfCarryingMostCalories(Util.getInputAsList(1)))
    }

}