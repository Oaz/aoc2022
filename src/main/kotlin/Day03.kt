

object Day03 {
  
  fun itemPriority(item: Char): Int {
    if (item.isUpperCase()) return item.code - 'A'.code + 27
    return item.code - 'a'.code + 1
  }
  
  fun commonItem(content: String): Char {
    val half = content.length / 2
    val firstPart = content.substring(0, half).toSet()
    val secondPart = content.substring(half).toSet()
    return firstPart.intersect(secondPart).single()
  }
  
  fun makeGroups(input: Iterable<String>): Iterable<Iterable<String>> = input.chunked(3)
  
  fun uniqueItemInGroup(group: Iterable<String>): Char = group.map(String::toSet).reduce(Set<Char>::intersect).single()
  
}
