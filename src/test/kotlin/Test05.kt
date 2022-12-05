import Day05.Crate
import Day05.Move
import Day05.ShipWithCrateMover9000
import Day05.ShipWithCrateMover9001
import Day05.getOnlyUsefulCharInCrateRow
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal object Test05 {
  
  private val example = listOf(
    "    [D]",
    "[N] [C]",
    "[Z] [M] [P]",
    " 1   2   3 ",
    "",
    "move 1 from 2 to 1",
    "move 3 from 1 to 3",
    "move 2 from 2 to 1",
    "move 1 from 1 to 2",
  )
  
  private val exampleExpectedCrates = listOf(
    listOf(Crate('N'), Crate('Z')),
    listOf(Crate('D'), Crate('C'), Crate('M')),
    listOf(Crate('P')),
  )
  
  @Test
  internal fun `filter crate useful chars`() {
    assertEquals("ZMP", getOnlyUsefulCharInCrateRow("[Z] [M] [P]"))
    assertEquals(" D", getOnlyUsefulCharInCrateRow("    [D]"))
  }
  
  @Test
  internal fun `read crates`() {
    val (actualCrates, _) = Day05.read(example)
    assertEquals(exampleExpectedCrates, actualCrates)
  }
  
  private val exampleExpectedMoves = listOf(
    Move(1, 2, 1),
    Move(3, 1, 3),
    Move(2, 2, 1),
    Move(1, 1, 2),
  )
  
  @Test
  internal fun `read moves`() {
    val (_, actualMoves) = Day05.read(example)
    assertEquals(exampleExpectedMoves, actualMoves)
  }
  
  @Test
  internal fun `execute single move in part1`() {
    val (oldCrates, _) = Day05.read(example)
    val newCrates = ShipWithCrateMover9000(oldCrates).execute(Move(2, 2, 1)).stacks
    assertEquals(listOf(Crate('C'), Crate('D'), Crate('N'), Crate('Z')), newCrates[0])
    assertEquals(listOf(Crate('M')), newCrates[1])
  }
  
  
  private val exampleExpectedCratesAfterAllMovesPart1 = listOf(
    listOf(Crate('C')),
    listOf(Crate('M')),
    listOf(Crate('Z'), Crate('N'), Crate('D'), Crate('P')),
  )
  
  @Test
  internal fun `execute all moves in part1`() {
    val (oldCrates, moves) = Day05.read(example)
    val newCrates = ShipWithCrateMover9000(oldCrates).execute(moves)
    assertEquals(exampleExpectedCratesAfterAllMovesPart1, newCrates.stacks)
    assertEquals("CMZ", newCrates.signature())
  }
  
  @Test
  internal fun part1() {
    val (oldCrates, moves) = Day05.read(Util.getInputAsList(5))
    val newCrates = ShipWithCrateMover9000(oldCrates).execute(moves)
    assertEquals("FWNSHLDNZ", newCrates.signature())
  }
  
  @Test
  internal fun `execute single move in part2`() {
    val (oldCrates, _) = Day05.read(example)
    val newCrates = ShipWithCrateMover9001(oldCrates).execute(Move(2, 2, 1)).stacks
    assertEquals(listOf(Crate('D'), Crate('C'), Crate('N'), Crate('Z')), newCrates[0])
    assertEquals(listOf(Crate('M')), newCrates[1])
  }
  
  
  private val exampleExpectedCratesAfterAllMovesPart2 = listOf(
    listOf(Crate('M')),
    listOf(Crate('C')),
    listOf(Crate('D'), Crate('N'), Crate('Z'), Crate('P')),
  )
  
  @Test
  internal fun `execute all moves in part2`() {
    val (oldCrates, moves) = Day05.read(example)
    val newCrates = ShipWithCrateMover9001(oldCrates).execute(moves)
    assertEquals(exampleExpectedCratesAfterAllMovesPart2, newCrates.stacks)
    assertEquals("MCD", newCrates.signature())
  }
  
  @Test
  internal fun part2() {
    val (oldCrates, moves) = Day05.read(Util.getInputAsList(5))
    val newCrates = ShipWithCrateMover9001(oldCrates).execute(moves)
    assertEquals("RNRGDNFQG", newCrates.signature())
  }
  
}


