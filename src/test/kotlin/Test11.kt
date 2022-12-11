import Day11.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test11 {
  
  @JvmStatic
  private fun expectedOperations() = listOf(
    "old + 3" to 13,
    "old + 7" to 17,
    "old * 3" to 30,
    "old * 7" to 70,
    "old * old" to 100,
  )
  @ParameterizedTest
  @MethodSource("expectedOperations")
  internal fun `check operations`(x: Pair<String,Int>) {
    val operation = Day11.readOperation(x.first)
    assertEquals(x.second.toLong(), operation(10))
  }
  
  private val example = listOf(
    "Monkey 0:",
    "  Starting items: 79, 98",
    "  Operation: new = old * 19",
    "  Test: divisible by 23",
    "    If true: throw to monkey 2",
    "    If false: throw to monkey 3",
    "",
    "Monkey 1:",
    "  Starting items: 54, 65, 75, 74",
    "  Operation: new = old + 6",
    "  Test: divisible by 19",
    "    If true: throw to monkey 2",
    "    If false: throw to monkey 0",
    "",
    "Monkey 2:",
    "  Starting items: 79, 60, 97",
    "  Operation: new = old * old",
    "  Test: divisible by 13",
    "    If true: throw to monkey 1",
    "    If false: throw to monkey 3",
    "",
    "Monkey 3:",
    "  Starting items: 74",
    "  Operation: new = old + 3",
    "  Test: divisible by 17",
    "    If true: throw to monkey 0",
    "    If false: throw to monkey 1",
    "",
  )
  
  @JvmStatic
  private fun expectedInitialInspectsForExample() = listOf(
    MonkeyInspect(0,79) to MonkeyInspect(3,500),
    MonkeyInspect(0,98) to MonkeyInspect(3,620),
    MonkeyInspect(1,54) to MonkeyInspect(0,20),
    MonkeyInspect(1,65) to MonkeyInspect(0,23),
    MonkeyInspect(1,75) to MonkeyInspect(0,27),
    MonkeyInspect(1,74) to MonkeyInspect(0,26),
    MonkeyInspect(2,79) to MonkeyInspect(1,2080),
    MonkeyInspect(2,60) to MonkeyInspect(3,1200),
    MonkeyInspect(2,97) to MonkeyInspect(3,3136),
    MonkeyInspect(3,74) to MonkeyInspect(1,25),
  )
  
  @Test
  internal fun `read input in example`() {
    val sim = Day11Part1(example)
    assertEquals(4, sim.monkeys.count())
    assertEquals(expectedInitialInspectsForExample().map { it.first }, sim.initialInspects)
  }
  
  @ParameterizedTest
  @MethodSource("expectedInitialInspectsForExample")
  internal fun `verify inspections`(x: Pair<MonkeyInspect,MonkeyInspect>) {
    val sim = Day11Part1(example)
    assertEquals(x.second, sim.proceed(x.first))
  }
  
  private val expectedHoldingsForExample = listOf(
    listOf(
      makeHolding(0, listOf(79, 98),0),
      makeHolding(1, listOf(54, 65, 75, 74),0),
      makeHolding(2, listOf(79, 60, 97),0),
      makeHolding(3, listOf(74),0),
    ),
    listOf(
      makeHolding(0, listOf(20, 23, 27, 26),2),
      makeHolding(1, listOf(2080, 25, 167, 207, 401, 1046),4),
      makeHolding(2, listOf(),3),
      makeHolding(3, listOf(),5),
    ),
    listOf(
      makeHolding(0, listOf(695, 10, 71, 135, 350),6),
      makeHolding(1, listOf(43, 49, 58, 55, 362),10),
      makeHolding(2, listOf(),4),
      makeHolding(3, listOf(),10),
    ),
    listOf(
      makeHolding(0, listOf(16, 18, 21, 20, 122),11),
      makeHolding(1, listOf(1468, 22, 150, 286, 739),15),
      makeHolding(2, listOf(),4),
      makeHolding(3, listOf(),15),
    ),
  )
  private fun makeHolding(id: Int, items: List<Int>, activity:Long): Focus = Focus(id, items.map { MonkeyInspect(id,it.toLong()) },activity)
  
  @Test
  internal fun `play example`() {
    val sim = Day11Part1(example)
    assertEquals(expectedHoldingsForExample[0], sim.initialFocuses)
    val holding1 = sim.proceed(sim.initialFocuses)
    assertEquals(expectedHoldingsForExample[1], holding1)
    val holding2 = sim.proceed(holding1)
    assertEquals(expectedHoldingsForExample[2], holding2)
    val holding3 = sim.proceed(holding2)
    assertEquals(expectedHoldingsForExample[3], holding3)
  }
  
  @Test
  internal fun `count activity on example`() {
    val sim = Day11Part1(example)
    val result = sim.playAll()
    assertEquals( listOf(
      makeHolding(0, listOf(10, 12, 14, 26, 34),101),
      makeHolding(1, listOf(245, 93, 53, 199, 115),95),
      makeHolding(2, listOf(),7),
      makeHolding(3, listOf(),105),
    ), result)
    assertEquals(10605, result.monkeyBusiness())
  }
  
  @Test
  internal fun part1() {
    val sim = Day11Part1(Util.getInputAsList(11))
    assertEquals(107822, sim.playAll().monkeyBusiness())
  }
  
  @Test
  internal fun `play example on part 2`() {
    val sim = Day11Part2(example)
    assertEquals(listOf<Long>(2,4,3,6), sim.play(1).map { it.activity })
    assertEquals(listOf<Long>(99,97,8,103), sim.play(20).map { it.activity })
  }
  
  @Test
  internal fun `monkeyBusiness on example part 2`() {
    val sim = Day11Part2(example)
    assertEquals(2713310158, sim.playAll().monkeyBusiness())
  }
  
  @Test
  internal fun part2() {
    val sim = Day11Part2(Util.getInputAsList(11))
    assertEquals(27267163742, sim.playAll().monkeyBusiness())
  }

}


