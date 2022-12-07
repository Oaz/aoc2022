import Day07.File
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal object Test07 {
  
  @JvmStatic
  private fun folderNavigation() = listOf(
    Triple("","/","/"),
    Triple("/","a","/a/"),
    Triple("/a/","e","/a/e/"),
    Triple("/a/e/","..","/a/"),
    Triple("/a/","..","/"),
    Triple("/","d","/d/"),
  )
  @ParameterizedTest
  @MethodSource("folderNavigation")
  fun `folder navigation`(x : Triple<String,String,String>) {
    assertEquals(x.third, Day07.navigate(x.first, x.second))
  }
  
  @JvmStatic
  private fun folderContent() = listOf(
    Pair("14848514 b.txt\n", listOf(File("b.txt", 14848514))),
    Pair("14848514 b.txt\n8504156 c.dat\n",listOf(File("b.txt", 14848514),File("c.dat", 8504156))),
    Pair("dir a\n14848514 b.txt\n8504156 c.dat\n",listOf(File("b.txt", 14848514),File("c.dat", 8504156))),
    Pair("dir a\n14848514 b.txt\n8504156 c.dat\ndir d\n",listOf(File("b.txt", 14848514),File("c.dat", 8504156))),
  )
  @ParameterizedTest
  @MethodSource("folderContent")
  fun `parse folder content`(x : Pair<String,List<File>>) {
    assertEquals(x.second, Day07.folderContent(x.first))
  }
  
  
  val example = "\$ cd /\n" +
      "\$ ls\n" +
      "dir a\n" +
      "14848514 b.txt\n" +
      "8504156 c.dat\n" +
      "dir d\n" +
      "\$ cd a\n" +
      "\$ ls\n" +
      "dir e\n" +
      "29116 f\n" +
      "2557 g\n" +
      "62596 h.lst\n" +
      "\$ cd e\n" +
      "\$ ls\n" +
      "584 i\n" +
      "\$ cd ..\n" +
      "\$ cd ..\n" +
      "\$ cd d\n" +
      "\$ ls\n" +
      "4060174 j\n" +
      "8033020 d.log\n" +
      "5626152 d.ext\n" +
      "7214296 k\n"
  
  private val expectedFileSystemForExample = listOf(
    "/" to listOf(
      File("b.txt", 14848514),
      File("c.dat", 8504156),
    ),
    "/a/" to listOf(
      File("f", 29116),
      File("g", 2557),
      File("h.lst", 62596),
    ),
    "/a/e/" to listOf(
      File("i", 584),
    ),
    "/d/" to listOf(
      File("j", 4060174),
      File("d.log", 8033020),
      File("d.ext", 5626152),
      File("k", 7214296),
    ),
  )
  @Test
  internal fun `find all folders in example`() {
    val filesystem = Day07(example)
    assertEquals(expectedFileSystemForExample, filesystem.folderContent.toList())
  }
  
  @JvmStatic
  private fun folderSizes() = listOf(
    Pair("/", 48381165),
    Pair("/a/", 94853),
    Pair("/a/e/", 584),
    Pair("/d/", 24933642),
  )
  @ParameterizedTest
  @MethodSource("folderSizes")
  fun `compute folder total sizes in example`(x : Pair<String,Long>) {
    val filesystem = Day07(example)
    assertEquals(x.second, Day07.computeTotalSizeFor(x.first, filesystem.folderContent))
  }
  
  @Test
  internal fun `total sizes for folders smaller than 100000 in example`() {
    val filesystem = Day07(example)
    assertEquals(95437, filesystem.totalSizeForFoldersUnder100000())
  }
  
  @Test
  internal fun part1() {
    val filesystem = Day07(Util.getInputAsString(7))
    assertEquals(1443806, filesystem.totalSizeForFoldersUnder100000())
  }
  
  @Test
  internal fun `size of folder to delete in example`() {
    val filesystem = Day07(example)
    assertEquals(24933642, filesystem.sizeOfFolderToDelete())
  }
  
  @Test
  internal fun part2() {
    val filesystem = Day07(Util.getInputAsString(7))
    assertEquals(942298, filesystem.sizeOfFolderToDelete())
  }
}


