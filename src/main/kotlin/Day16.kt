class Day16(input: List<String>) {
  
  private val valves =
    input.map(::readValveData).sortedByDescending { it.pressure }.mapIndexed { index, data -> Valve(index, data) }
  private val valvesByName: Map<String, Valve> = valves.associateBy { it.name }
  private val neighborsOfValve = valves.associateWith { v -> v.neighborNames.map { valvesByName[it]!! } }
  private val startingValve = valvesByName["AA"]!!
  
  fun mostPressureAlone() = searchAlone().path.sumOf { it.flow.throughput }
  fun searchAlone() = search(Worker(startingValve, Action.Wait), 30)
  fun mostPressureInDuo() = searchInDuo().path.sumOf { it.flow.throughput }
  fun searchInDuo() = search(Duo(Worker(startingValve, Action.Wait), Worker(startingValve, Action.Wait)), 26)
  
  private fun search(team: Team, remaining: Int): AStar.Result<State> {
    val closedValves = valves.filter { it.pressure > 0 }
    val initialState = State(team, Flow.create(closedValves, remaining))
    val aStar = AStar.on<State>()
      .withHeuristic { 0 }
      .withNeighborsDistance { state ->
        state.neighbors(neighborsOfValve).map { it to it.flow.missingPressures * it.flow.remainingTime }
      }.build()
    return aStar.search(initialState) { it.flow.remainingTime == 0 }
  }
  
  data class State(val team: Team, val flow: Flow) {
    val signature = team.signature + (flow.signature shl 16)
    fun neighbors(network: Map<Valve, List<Valve>>) =
      if (flow.isAllClosed()) listOf(copy(team = team.waits(), flow = flow.forward()))
      else team.options(network, flow).map { copy(team = it, flow = flow.open(it)) }
    
    fun info() = Info(team.description(), flow.remainingTime, flow.throughput)
    override fun equals(other: Any?) = (this === other) || ((other is State) && (signature == other.signature))
    override fun hashCode() = signature
  }
  
  data class Info(val text: String, val remaining: Int, val throughput: Int)
  
  interface Team {
    val signature: Int
    fun waits(): Team
    fun options(network: Map<Valve, List<Valve>>, flow: Flow): List<Team>
    fun valveOpening(): Int
    fun throughput(): Int
    fun description(): String
  }
  
  data class Worker(val valve: Valve, val action: Action) : Team {
    override val signature = (valve.index shl 2) + action.id()
    override fun waits() = copy(action = Action.Wait)
    override fun options(network: Map<Valve, List<Valve>>, flow: Flow) =
      if (flow.canOpen(valve)) movesOrOpens(network) else moves(network)
    
    override fun valveOpening() = action.signature(valve)
    override fun throughput() = if (action == Action.Open) valve.pressure else 0
    override fun description() = action.text(valve)
    private fun moves(network: Map<Valve, List<Valve>>) =
      network[valve]!!.map { copy(valve = it, action = Action.MoveTo) }
    
    private fun movesOrOpens(network: Map<Valve, List<Valve>>) = moves(network) + copy(action = Action.Open)
  }
  
  data class Duo(val worker1: Worker, val worker2: Worker) : Team {
    override val signature = (worker1.signature shl 8) + worker2.signature
    override fun waits() = Duo(worker1.waits(), worker2.waits())
    override fun options(network: Map<Valve, List<Valve>>, flow: Flow): List<Team> {
      val options1 = worker1.options(network, flow)
      val options2 = worker2.options(network, flow)
      return options1.flatMap { w1 -> options2.map { w2 -> Duo(w1, w2) } }
    }
    
    override fun valveOpening() = worker1.valveOpening() + worker2.valveOpening()
    override fun throughput() = worker1.throughput() + worker2.throughput()
    override fun description() = "You ${worker1.description()}, elephant ${worker2.description()}"
  }
  
  data class Flow private constructor(
    private val stillOpenValves: Int,
    private val pressures: IntArray,
    val remainingTime: Int,
    val nextThroughput: Int = 0,
    val throughput: Int = 0
  ) {
    val signature = remainingTime + (stillOpenValves shl 5)
    val missingPressures = pressures.filterIndexed { index, _ -> canOpen(Valve.maskForIndex(index)) }.sum()
    fun isAllClosed() = stillOpenValves == 0
    fun canOpen(valve: Valve) = canOpen(valve.mask)
    private fun canOpen(mask: Int) = (mask and stillOpenValves) != 0
    fun forward() = copy(remainingTime = remainingTime - 1, throughput = nextThroughput)
    fun open(team: Team) =
      copy(
        remainingTime = remainingTime - 1, throughput = nextThroughput,
        nextThroughput = nextThroughput + team.throughput(), stillOpenValves = stillOpenValves - team.valveOpening()
      )
    
    override fun equals(other: Any?) = (this === other) || ((other is Flow) && (signature == other.signature))
    override fun hashCode() = signature
    
    companion object {
      fun create(closedValves: List<Valve>, remainingTime: Int): Flow {
        val allOpen = closedValves.sumOf { it.mask }
        val pressures = closedValves.sortedBy { it.index }.map { it.pressure }.toIntArray()
        return Flow(allOpen, pressures, remainingTime)
      }
    }
  }
  
  enum class Action {
    MoveTo {
      override fun text(valve: Valve) = "move to ${valve.name}"
      override fun signature(valve: Valve) = 0
      override fun id() = 0
    },
    Open {
      override fun text(valve: Valve) = "open ${valve.name}"
      override fun signature(valve: Valve) = valve.mask
      override fun id() = 1
    },
    Wait {
      override fun text(valve: Valve) = "wait"
      override fun signature(valve: Valve) = 0
      override fun id() = 2
    };
    
    abstract fun text(valve: Valve): String
    abstract fun signature(valve: Valve): Int
    abstract fun id(): Int
  }
  
  class Valve(val index: Int, data: ValveData) {
    val name = data.name
    val pressure = data.pressure
    val neighborNames = data.neighborNames
    val mask = if (pressure > 0) maskForIndex(index) else 0
    
    companion object {
      fun maskForIndex(index: Int) = 1 shl index
    }
  }
  
  data class ValveData(val name: String, val pressure: Int, val neighborNames: List<String>)
  
  companion object {
    fun readValveData(input: String): ValveData {
      val g = regex.matchEntire(input)
      return ValveData(g.at(1), g.at(2).toInt(), g.at(3).split(", "))
    }
    
    private val regex = """Valve (.+) has flow rate=(\d+); tunnel.? lead.? to valve.? (.+)""".toRegex()
    private fun MatchResult?.at(index: Int): String = this?.groups?.get(index)?.value!!
  }
  
}

