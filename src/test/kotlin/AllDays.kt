import Day04.oneRangeFullyContainTheOther
import Day04.rangesOverlap
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal object AllDays {
  
  @Test
  internal fun day01_part1() {
    assertEquals(69289, Day01.caloriesForElfCarryingMostCalories(Util.getInputAsList(1)))
  }

  @Test
  internal fun day01_part2() {
    assertEquals(205615, Day01.caloriesForTop3ElfCarryingMostCalories(Util.getInputAsList(1)))
  }
  
  @Test
  internal fun day02_part1() {
    assertEquals(9651, Util.getInputAsList(2).sumOf(Day02::part1Score))
  }
  
  @Test
  internal fun day02_part2() {
    assertEquals(10560, Util.getInputAsList(2).sumOf(Day02::part2Score))
  }
  
  @Test
  internal fun day03_part1() {
    assertEquals(8185, Util.getInputAsList(3).map(Day03::commonItem).sumOf(Day03::itemPriority))
  }
  
  @Test
  internal fun day03_part2() {
    assertEquals(2817, Day03.makeGroups(Util.getInputAsList(3)).map(Day03::uniqueItemInGroup).sumOf(Day03::itemPriority))
  }
  
  @Test
  internal fun day04_part1() {
    assertEquals(562, Day04.parseAndCount(Util.getInputAsList(4)) { it.oneRangeFullyContainTheOther() })
  }
  
  @Test
  internal fun day04_part2() {
    assertEquals(924, Day04.parseAndCount(Util.getInputAsList(4)) { it.rangesOverlap() })
  }
  
  @Test
  internal fun day05_part1() {
    val (oldCrates, moves) = Day05.read(Util.getInputAsList(5))
    val newCrates = Day05.ShipWithCrateMover9000(oldCrates).execute(moves)
    assertEquals("FWNSHLDNZ", newCrates.signature())
  }
  
  @Test
  internal fun day05_part2() {
    val (oldCrates, moves) = Day05.read(Util.getInputAsList(5))
    val newCrates = Day05.ShipWithCrateMover9001(oldCrates).execute(moves)
    assertEquals("RNRGDNFQG", newCrates.signature())
  }
  
  @Test
  internal fun day06_part1() {
    assertEquals(1987, Day06.startOfPacket(Util.getInputAsString(6)))
  }
  
  @Test
  internal fun day06_part2() {
    assertEquals(3059, Day06.startOfMessage(Util.getInputAsString(6)))
  }
  
  @Test
  internal fun day07_part1() {
    val filesystem = Day07(Util.getInputAsString(7))
    assertEquals(1443806, filesystem.totalSizeForFoldersUnder100000())
  }
  
  @Test
  internal fun day07_part2() {
    val filesystem = Day07(Util.getInputAsString(7))
    assertEquals(942298, filesystem.sizeOfFolderToDelete())
  }
  
  @Test
  internal fun day08_part1() {
    val forest = Day08(Util.getInputAsList(8))
    assertEquals(1859, forest.countVisible())
  }
  
  @Test
  internal fun day08_part2() {
    val forest = Day08(Util.getInputAsList(8))
    assertEquals(332640, forest.scenicScore())
  }
  
  @Test
  internal fun day09_part1() {
    val sim = Day09(Util.getInputAsList(9))
    assertEquals(6190, sim.uniqueShortRopeTailPositions())
  }
  
  @Test
  internal fun day09_part2() {
    val sim = Day09(Util.getInputAsList(9))
    assertEquals(2516, sim.uniqueLongRopeTailPositions())
  }
  
  @Test
  internal fun day10_part1() {
    val sim = Day10(Util.getInputAsList(10))
    assertEquals(13680, sim.sumOfStrengths)
  }
  
  @Test
  internal fun day10_part2() {
    val sim = Day10(Util.getInputAsList(10))
    assertEquals(listOf(
      "###..####..##..###..#..#.###..####.###..",
      "#..#....#.#..#.#..#.#.#..#..#.#....#..#.",
      "#..#...#..#....#..#.##...#..#.###..###..",
      "###...#...#.##.###..#.#..###..#....#..#.",
      "#....#....#..#.#....#.#..#....#....#..#.",
      "#....####..###.#....#..#.#....####.###..",
    ), sim.screen)
  }
  
  @Test
  internal fun day11_part1() {
    val sim = Day11Part1(Util.getInputAsList(11))
    assertEquals(107822, sim.playAll().monkeyBusiness())
  }
  
  @Test
  internal fun day11_part2() {
    val sim = Day11Part2(Util.getInputAsList(11))
    assertEquals(27267163742, sim.playAll().monkeyBusiness())
  }
  
  @Test
  internal fun day12_part1() {
    val sim = Day12(Util.getInputAsList(12))
    assertEquals(383, sim.fewestStepsToEnd().length)
  }
  
  @Test
  internal fun day12_part2() {
    val sim = Day12(Util.getInputAsList(12))
    assertEquals(377, sim.fewestStepsToMultipleStartChoices().length)
  }
  
  @Test
  internal fun day13_part1() {
    val packets = Day13(Util.getInputAsList(13))
    MatcherAssert.assertThat(packets.sumOfIndicesOfPairsInRightOrder(), `is`(CoreMatchers.equalTo(5675)))
  }
  
  @Test
  internal fun day13_part2() {
    val packets = Day13(Util.getInputAsList(13))
    MatcherAssert.assertThat(packets.decoderKey(), `is`(CoreMatchers.equalTo(20383)))
  }
  @Test
  internal fun day14_part1() {
    val sim = Day14(Util.getInputAsList(14))
    assertEquals(719, sim.pourUntilVoid())
  }
  
  @Test
  internal fun day14_part2() {
    val sim = Day14(Util.getInputAsList(14))
    assertEquals(23390, sim.fasterPourUntilBlocked())
  }
  
  @Test
  internal fun day15_part1() {
    val sim = Day15(Util.getInputAsList(15))
    assertEquals(4961647, sim.positionsWithNoBeaconInRow(2000000))
  }
  
  @Disabled
  @Test
  internal fun day15_part2() {
    val sim = Day15(Util.getInputAsList(15))
    assertEquals(12274327017867, sim.tuningFrequency(4000000))
  }
  
  @Test
  internal fun day16_part1() {
    val sim = Day16(Util.getInputAsList(16))
    assertEquals(1701, sim.mostPressureAlone())
  }
  
  @Disabled
  @Test
  internal fun day16_part2() {
    val sim = Day16(Util.getInputAsList(16))
    assertEquals(2455, sim.mostPressureInDuo())
  }
  
  @Test
  internal fun day17_part1() {
    val sim = Day17(Util.getInputAsString(17))
    assertEquals(3173, sim.findHeight(2022))
  }
  
  @Test
  internal fun day17_part2() {
    val sim = Day17(Util.getInputAsString(17))
    assertEquals(1570930232582, sim.findHeight(1000000000000))
  }
}


