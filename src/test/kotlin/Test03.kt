import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*

internal object Test03 {
  
  data class ItemPriorityCheck(val item: Char,val expectedPriority: Int)
  @JvmStatic
  private fun itemPriority() = listOf(
    ItemPriorityCheck('a',1),
    ItemPriorityCheck('z',26),
    ItemPriorityCheck('A',27),
    ItemPriorityCheck('Z',52),
  )
  @ParameterizedTest
  @MethodSource("itemPriority")
  internal fun `Find item priority`(x : ItemPriorityCheck)
  {
    assertEquals(x.expectedPriority, Day03.itemPriority(x.item))
  }
  
  data class RucksackContent(val content: String,val expectedCommonItem: Char)
  @JvmStatic
  private fun commonItem() = listOf(
    RucksackContent("vJrwpWtwJgWrhcsFMMfFFhFp",'p'),
    RucksackContent("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",'L'),
    RucksackContent("PmmdzqPrVvPwwTWBwg",'P'),
    RucksackContent("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",'v'),
    RucksackContent("ttgJtRGJQctTZtZT",'t'),
    RucksackContent("CrZsJsPPZsGzwwsLwLmpwMDw",'s'),
  )
  @ParameterizedTest
  @MethodSource("commonItem")
  internal fun `Find common item`(x : RucksackContent)
  {
    assertEquals(x.expectedCommonItem, Day03.commonItem(x.content))
  }
  
  @Test
  internal fun `result on example for part 1`() {
    assertEquals(157, commonItem().map { Day03.commonItem(it.content) }.sumOf(Day03::itemPriority))
  }
  @Test
  internal fun `part 1`() {
    assertEquals(8185, Util.getInputAsList(3).map(Day03::commonItem).sumOf(Day03::itemPriority))
  }
  
  @Test
  internal fun `result on example for part 2`() {
    val example = commonItem().map(RucksackContent::content)
    assertEquals(70, Day03.makeGroups(example).map(Day03::uniqueItemInGroup).sumOf(Day03::itemPriority))
  }
  @Test
  internal fun `part 2`() {
    assertEquals(2817, Day03.makeGroups(Util.getInputAsList(3)).map(Day03::uniqueItemInGroup).sumOf(Day03::itemPriority))
  }
}


