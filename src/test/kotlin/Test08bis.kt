import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal object Test08bis {
  
  private val example = listOf(
    "30373",
    "25512",
    "65332",
    "33549",
    "35390",
  )
  
  private val expectedHeightsForExample = arrayListOf(
    arrayListOf(3,0,3,7,3),
    arrayListOf(2,5,5,1,2),
    arrayListOf(6,5,3,3,2),
    arrayListOf(3,3,5,4,9),
    arrayListOf(3,5,3,9,0),
  )
  @Test
  internal fun `read example`() {
    val forest = Day08bis(example)
    assertEquals(expectedHeightsForExample, forest.heights)
  }
  
  private val expectedVisibilityForExample = arrayListOf(
    arrayListOf(true,true,true,true,true),
    arrayListOf(true,true,true,false,true),
    arrayListOf(true,true,false,true,true),
    arrayListOf(true,false,true,false,true),
    arrayListOf(true,true,true,true,true),
  )
  @Test
  internal fun `check visibilities example`() {
    val forest = Day08bis(example)
    val visibilities = ArrayList( forest.trees.map { row ->
      ArrayList(row.map { it.isVisible() })
    })
    assertEquals(expectedVisibilityForExample, visibilities)
    assertEquals(21, forest.countVisible())
  }
  
  @Test
  internal fun part1() {
    val forest = Day08bis(Util.getInputAsList(8))
    assertEquals(1859, forest.countVisible())
  }
  
  @Test
  internal fun `check viewing distances in example`() {
    val forest = Day08bis(example)
    assertEquals(4, forest.trees[1][2].viewingDistance())
    assertEquals(8, forest.trees[3][2].viewingDistance())
    assertEquals(6, forest.trees[2][1].viewingDistance())
    assertEquals(8, forest.scenicScore())
  }
  
  @Test
  internal fun part2() {
    val forest = Day08bis(Util.getInputAsList(8))
    assertEquals(332640, forest.scenicScore())
  }
}


