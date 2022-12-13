import arrow.core.Either

data class ComparableTree<LEAF:Comparable<LEAF>>(val children: List<Either<LEAF, ComparableTree<LEAF>>>) : Comparable<ComparableTree<LEAF>> {
  companion object {
    fun <LEAF : Comparable<LEAF>> leaf(l : LEAF) : Either<LEAF, ComparableTree<LEAF>> =
      Either.left(l)
    fun <LEAF : Comparable<LEAF>> node(children: List<Either<LEAF, ComparableTree<LEAF>>>) : Either<LEAF, ComparableTree<LEAF>> =
      Either.right(ComparableTree(children))
    fun <LEAF : Comparable<LEAF>> node(vararg children: Either<LEAF, ComparableTree<LEAF>>) : Either<LEAF, ComparableTree<LEAF>> =
      Either.right(ComparableTree(children.toList()))
  }
  override fun compareTo(other: ComparableTree<LEAF>)= children.compareTo(other.children)
  private fun List<Either<LEAF, ComparableTree<LEAF>>>.compareTo(other: List<Either<LEAF, ComparableTree<LEAF>>>): Int = when {
    isEmpty() && other.isEmpty() -> 0
    isEmpty() && other.isNotEmpty() -> -1
    isNotEmpty() && other.isEmpty() -> 1
    else -> first().compareTo(other.first()).orIfEqual { drop(1).compareTo(other.drop(1)) }
  }
  private fun Int.orIfEqual(orElse: () -> Int) = if (this != 0) this else orElse()
  private fun Either<LEAF, ComparableTree<LEAF>>.compareTo(other: Either<LEAF, ComparableTree<LEAF>>) =
    compareTo(other) { ComparableTree(listOf(Either.left(it))) }
  
  private fun <A : Comparable<A>, B : Comparable<B>> Either<A, B>.compareTo(other: Either<A, B>, map: (A) -> B) =
    collapse(
      { thisLeft ->
        other.collapse(
          { otherLeft -> thisLeft.compareTo(otherLeft) },
          { otherRight -> map(thisLeft).compareTo(otherRight) }
        )
      },
      { thisRight ->
        other.collapse(
          { otherLeft -> thisRight.compareTo(map(otherLeft)) },
          { otherRight -> thisRight.compareTo(otherRight) }
        )
      }
    )
  private fun <A, B, C> Either<A, B>.collapse(fa: (A) -> C, fb: (B) -> C): C = bimap(fa, fb).fold({ it }, { it })
}