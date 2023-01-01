class Day21(input: List<String>) {
  private val monkeyByName = input.map(::createMonkey).associateBy { it.name }
  private val monkeys =
    kahnTopologicalSort(monkeyByName.keys.toList()) { monkeyByName[it]!!.predecessors }.map { monkeyByName[it]!! }
  private val root = "root"
  
  fun findRootYell(): Long {
    val allYelled = monkeys.fold(mapOf<String, Long>()) { yelled, monkey -> monkey.compute(yelled) }
    return allYelled[root]!!
  }
  
  fun findHumanYell(): Long {
    val human = "humn"
    val realMonkeys = monkeys.filter { it.name != human }
    val canBeComputedWithoutHuman =
      realMonkeys.fold(setOf<String>()) { computable, monkey ->
        if (monkey.predecessors.all { it in computable }) computable + monkey.name else computable
      }
    val (forward, backward) = realMonkeys.partition { it.name in canBeComputedWithoutHuman }
    val forwardYelled = forward.fold(mapOf<String, Long>()) { yelled, monkey -> monkey.compute(yelled) }
    val allYelled = backward.reversed().map { it as JobMonkey }
      .map { if (it.name == root) RootEqualityMonkey(it) else BackwardJobMonkey(it) }
      .fold(forwardYelled) { yelled, monkey -> monkey.compute(yelled) }
    return allYelled[human]!!
  }
  
  interface Monkey {
    val name: String
    fun compute(yelled: Map<String, Long>): Map<String, Long>
  }
  
  interface ForwardMonkey : Monkey {
    val predecessors: List<String>
  }
  
  data class NumberMonkey(override val name: String, val number: Long) : ForwardMonkey {
    override val predecessors = listOf<String>()
    override fun compute(yelled: Map<String, Long>) = yelled + (name to number)
  }
  
  data class JobMonkey(override val name: String, val left: String, val operator: String, val right: String) :
    ForwardMonkey {
    val operation = when (operator) {
      "+" -> Operation.Add
      "-" -> Operation.Sub
      "*" -> Operation.Mul
      "/" -> Operation.Div
      else -> TODO()
    }
    override val predecessors = listOf(left, right)
    override fun compute(yelled: Map<String, Long>) =
      yelled + (name to operation.forward(yelled[left]!!, yelled[right]!!))
  }
  
  open class BackwardJobMonkey(private val monkey: JobMonkey) : Monkey {
    override val name = monkey.name
    override fun compute(yelled: Map<String, Long>): Map<String, Long> =
      if (monkey.left in yelled.keys) backward(monkey.right, monkey.left, yelled, monkey.operation.backwardRight)
      else backward(monkey.left, monkey.right, yelled, monkey.operation.backwardLeft)
    
    open fun backward(unknown: String, known: String, yelled: Map<String, Long>, op: (Long, Long) -> Long) =
      yelled + (unknown to op(yelled[name]!!, yelled[known]!!))
  }
  
  open class RootEqualityMonkey(monkey: JobMonkey) : BackwardJobMonkey(monkey) {
    override fun backward(unknown: String, known: String, yelled: Map<String, Long>, op: (Long, Long) -> Long) =
      yelled + (unknown to yelled[known]!!)
  }
  
  enum class Operation(
    val forward: (Long, Long) -> Long,
    val backwardLeft: (Long, Long) -> Long,
    val backwardRight: (Long, Long) -> Long
  ) {
    Add(Long::plus, Long::minus, Long::minus),
    Sub(Long::minus, Long::plus, { a, b -> b - a }),
    Mul(Long::times, Long::div, Long::div),
    Div(Long::div, Long::times, { a, b -> b / a }),
  }
  
  companion object {
    
    fun <T> kahnTopologicalSort(nodes: List<T>, incomingNodes: (T) -> List<T>): List<T> {
      val incoming = nodes.associateWith { incomingNodes(it) }.toMutableMap()
      val outgoing = incoming.inverse()
      var sorted = listOf<T>()
      val ready = ArrayDeque(nodes.filter { incoming[it]?.isEmpty() ?: true })
      while (ready.isNotEmpty()) {
        val smallest = ready.removeFirst()
        sorted = sorted + smallest
        for (bigger in outgoing.getOrDefault(smallest, listOf())) {
          incoming[bigger] = incoming[bigger]!! - smallest
          if (incoming[bigger]!!.isEmpty())
            ready.add(bigger)
        }
      }
      return sorted
    }
    
    fun <U, V> Map<U, List<V>>.inverse(): Map<V, List<U>> =
      flatMap { n -> n.value.map { it to n.key } }.groupBy { it.first }.mapValues { x -> x.value.map { it.second } }
    
    fun createMonkey(input: String): ForwardMonkey {
      val m = regex.matchEntire(input)!!
      return if (m.groups.filterNotNull().size == 3)
        NumberMonkey(m.at(1), m.at(2).toLong())
      else
        JobMonkey(m.at(1), m.at(4), m.at(5), m.at(6))
    }
    
    private val regex = """(.+): (\d+|((.+) (.) (.+)))""".toRegex()
    private fun MatchResult.at(index: Int) = this.groups[index]!!.value
    
  }
  
}