import ComparableTree.Companion.leaf
import ComparableTree.Companion.node
import Day13.*
import arrow.core.Either
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test13 {
  
  @JvmStatic
  private fun tokens() = listOf(
    "[1,1,3]" to listOf("[","1","1","3","]"),
    "[1,[],3]" to listOf("[","1","[","]","3","]"),
    "[41,[],23]" to listOf("[","41","[","]","23","]"),
  )
  @ParameterizedTest
  @MethodSource("tokens")
  internal fun tokenize(x: Pair<String, List<String>>) {
    assertEquals(x.second, Day13.tokenize(x.first).toList())
  }
  
  @JvmStatic
  private fun parseTree() = listOf(
    "[7]" to node(leaf(7)),
    "[12]" to node(leaf(12)),
    "[7,12,4]" to node(leaf(7),leaf(12),leaf(4)),
    "[[7,12,4]]" to node(node(leaf(7),leaf(12),leaf(4))),
    "[[[]]]" to node(node(node())),
    "[1,[2,[3,[4,[5,6,0]]]],8,9]" to node(
      leaf(1),
      node(
        leaf(2),
        node(
          leaf(3),
          node(
            leaf(4),
            node( leaf(5),leaf(6),leaf(0) ),
          ),
        ),
      ),
      leaf(8),
      leaf(9),
    ),
  )
  @ParameterizedTest
  @MethodSource("parseTree")
  internal fun `parse tree`(x: Pair<String, Either<Int,ComparableTree<Int>>>) {
    val actual = Day13.readPacket(x.first)
    assertEquals(x.second, Either.right(actual))
  }
  
  @JvmStatic
  private fun compareTree() = listOf(
    ("[1,1,3,1,1]" to "[1,1,5,1,1]") to -1,
    ("[[1],[2,3,4]]" to "[[1],4]") to -1,
    ("[9]" to "[[8,7,6]]") to 1,
    ("[[4,4],4,4]" to "[[4,4],4,4,4]") to -1,
    ("[7,7,7,7]" to "[7,7,7]") to 1,
    ("[]" to "[3]") to -1,
    ("[1,[2,[3,[4,[5,6,7]]]],8,9]" to "[1,[2,[3,[4,[5,6,0]]]],8,9]") to 1,
  )
  @ParameterizedTest
  @MethodSource("compareTree")
  internal fun `compare trees`(x: Pair<Pair<String, String>,Int>) {
    val t1 = Day13.readPacket(x.first.first)
    val t2 = Day13.readPacket(x.first.second)
    assertEquals(x.second, t1.compareTo(t2))
  }
  
  private val example = listOf(
    "[1,1,3,1,1]",
    "[1,1,5,1,1]",
    "",
    "[[1],[2,3,4]]",
    "[[1],4]",
    "",
    "[9]",
    "[[8,7,6]]",
    "",
    "[[4,4],4,4]",
    "[[4,4],4,4,4]",
    "",
    "[7,7,7,7]",
    "[7,7,7]",
    "",
    "[]",
    "[3]",
    "",
    "[[[]]]",
    "[[]]",
    "",
    "[1,[2,[3,[4,[5,6,7]]]],8,9]",
    "[1,[2,[3,[4,[5,6,0]]]],8,9]",
    "",
  )
  
  @Test
  internal fun `part 1 in example`() {
    val packets = Day13(example)
    assertThat(packets.sumOfIndicesOfPairsInRightOrder(), `is`(equalTo(13)))
  }

  @Test
  internal fun part1() {
    val packets = Day13(Util.getInputAsList(13))
    assertThat(packets.sumOfIndicesOfPairsInRightOrder(), `is`(equalTo(5675)))
  }

  @Test
  internal fun `part 2 in example`() {
    val packets = Day13(example)
    assertThat(packets.decoderKey(), `is`(equalTo(140)))
  }

  @Test
  internal fun part2() {
    val packets = Day13(Util.getInputAsList(13))
    assertThat(packets.decoderKey(), `is`(equalTo(20383)))
  }

}


