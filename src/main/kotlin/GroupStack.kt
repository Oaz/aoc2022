class GroupStack<T>(
  private val stack : List<T> = listOf(),
  private val sizes : List<Int> = listOf(0)
) {
  fun push(item: T) = GroupStack(stack + item, sizes.dropLast(1) + (sizes.last() + 1))
  fun open() = GroupStack(stack, sizes+ 0)
  fun top() = stack.takeLast(sizes.last())
  fun close(group:(List<T>) -> T): GroupStack<T> {
    val items = top()
    return GroupStack(stack.dropLast(items.count()), sizes.dropLast(1)).push(group(items))
  }
}
