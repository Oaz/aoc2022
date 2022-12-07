class Day07(private val input: String) {
  
  var folderContent = parseInputToGetFolders()
  private val folderTotalSize = folderContent.mapValues { folder -> computeTotalSizeFor(folder.key, folderContent)  }
  private val foldersTotalSizes = folderContent.keys.map { folderTotalSize[it]!! }
  
  private fun parseInputToGetFolders(): Map<String, List<File>> {
    val inputs = input.drop(2).split("$ ")
    var currentFolder = ""
    val buildFolders = mutableMapOf<String, List<File>>()
    for (row in inputs) {
      when (row.substring(0..1)) {
        "cd" -> currentFolder = navigate(currentFolder, row.substring(3).trim())
        "ls" -> buildFolders[currentFolder] = folderContent(row.substring(3))
        else -> throw Exception("Unknown command")
      }
    }
    return buildFolders.toMap()
  }
  
  fun totalSizeForFoldersUnder100000() = foldersTotalSizes.filter { it <= 100000 }.sumOf { it }
  
  fun sizeOfFolderToDelete(): Long {
    val unusedSpace = 70000000 - folderTotalSize["/"]!!
    val neededSpace = 30000000 - unusedSpace
    return foldersTotalSizes.filter { it >= neededSpace }.min()
  }
  
  data class File(val name: String, val size: Long)
  
  companion object {
    fun navigate(current: String, delta: String): String {
      if (current.isEmpty())
        return delta
      if (delta == "..")
        return current.substring(0..current.dropLast(1).indexOfLast { it == '/' })
      return "$current$delta/"
    }
    
    fun folderContent(input: String): List<File> =
      fileRegex.findAll(input).map {
        File(it.at(2), it.at(1).toLong())
      }.toList()
    
    private val fileRegex = """(\d+) (.+)\n""".toRegex()
    private fun MatchResult?.at(index: Int): String = this?.groups?.get(index)?.value!!
  
    fun computeTotalSizeFor(folderName: String, allFoldersContent: Map<String, List<File>>): Long =
      allFoldersContent.filter { it.key.startsWith(folderName) }.flatMap { it.value }.sumOf { it.size }
    
  }
  
}