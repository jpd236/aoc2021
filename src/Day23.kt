import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun getCost(value: String) = when(value) {
        "A" -> 1
        "B" -> 10
        "C" -> 100
        "D" -> 1000
        else -> error("unknown value $value")
    }

    data class State(
        // ordered front to back
        val aRooms: List<String>,
        val bRooms: List<String>,
        val cRooms: List<String>,
        val dRooms: List<String>,
        // ordered left to right
        val hallPositions: List<String>,
    ): Node<State> {
        fun getRooms(value: String) = when(value) {
            "A" -> aRooms
            "B" -> bRooms
            "C" -> cRooms
            "D" -> dRooms
            else -> error("unknown value $value")
        }

        fun getRoomIndex(value: String) = when(value) {
            "A" -> 2
            "B" -> 4
            "C" -> 6
            "D" -> 8
            else -> error("unknown value $value")
        }

        fun copy(value: String, newRooms: List<String>, newHallPositions: List<String>) = when(value) {
            "A" -> copy(aRooms = newRooms, hallPositions = newHallPositions)
            "B" -> copy(bRooms = newRooms, hallPositions = newHallPositions)
            "C" -> copy(cRooms = newRooms, hallPositions = newHallPositions)
            "D" -> copy(dRooms = newRooms, hallPositions = newHallPositions)
            else -> error("unknown value $value")
        }

        override fun getNeighbors(): List<Pair<State, Long>> {
            val neighbors = mutableListOf<Pair<State, Long>>()

            // For each room, can move the front-most member into a hall position.
            listOf("A", "B", "C", "D").forEach { value ->
                val rooms = getRooms(value)
                val roomIndex = getRoomIndex(value)
                for (rm in rooms.indices) {
                    if (rooms[rm].isNotEmpty()) {
                        listOf(
                            (roomIndex - 1 downTo 0) to { i: Int -> roomIndex - i },
                            (roomIndex + 1)..10 to { i: Int -> i - roomIndex }
                        ).forEach { (hallRange, stepFn) ->
                            for (i in hallRange) {
                                if (i == 2 || i == 4 || i == 6 || i == 8) continue
                                if (hallPositions[i].isNotEmpty()) break
                                val updatedHallPositions = hallPositions.toMutableList()
                                updatedHallPositions[i] = rooms[rm]
                                val updatedRooms = rooms.toMutableList()
                                updatedRooms[rm] = ""
                                val cost = getCost(rooms[rm]) * (1 + rm + stepFn(i)).toLong()
                                neighbors.add(copy(value, updatedRooms, updatedHallPositions) to cost)
                            }
                        }
                        break
                    }
                }
            }

            // For each hallway position, can move into the final room.
            hallPositions.forEachIndexed { i, value ->
                if (value.isEmpty()) return@forEachIndexed
                listOf("A", "B", "C", "D").forEach { destValue ->
                    if (value == destValue) {
                        val roomIndex = getRoomIndex(destValue)
                        val path = hallPositions.slice(min(i + 1, roomIndex)..max(i - 1, roomIndex))
                        val rooms = getRooms(destValue)
                        if (path.all { it.isEmpty() } && rooms.all { it.isEmpty() || it == destValue }) {
                            val firstEmpty = rooms.lastIndexOf("")
                            val updatedHallPositions = hallPositions.toMutableList()
                            updatedHallPositions[i] = ""
                            val updatedRooms = rooms.toMutableList()
                            updatedRooms[firstEmpty] = value
                            val cost = getCost(value) * (abs(i - roomIndex) + firstEmpty + 1).toLong()
                            neighbors.add(copy(value, updatedRooms, updatedHallPositions) to cost)
                        }
                    }
                }
            }
            return neighbors
        }
    }

    fun part1(input: List<String>) {
        val start = State(
            aRooms = listOf("${input[2][3]}", "${input[3][3]}"),
            bRooms = listOf("${input[2][5]}", "${input[3][5]}"),
            cRooms = listOf("${input[2][7]}", "${input[3][7]}"),
            dRooms = listOf("${input[2][9]}", "${input[3][9]}"),
            hallPositions = listOf("", "", "", "", "", "", "", "", "", "", "")
        )
        val goal = State(
            aRooms = listOf("A", "A"),
            bRooms = listOf("B", "B"),
            cRooms = listOf("C", "C"),
            dRooms = listOf("D", "D"),
            hallPositions = listOf("", "", "", "", "", "", "", "", "", "", "")
        )
        println(dijkstra(start, goal))
    }

    fun part2(input: List<String>) {
        val start = State(
            aRooms = listOf("${input[2][3]}", "D", "D", "${input[3][3]}"),
            bRooms = listOf("${input[2][5]}", "C", "B", "${input[3][5]}"),
            cRooms = listOf("${input[2][7]}", "B", "A", "${input[3][7]}"),
            dRooms = listOf("${input[2][9]}", "A", "C", "${input[3][9]}"),
            hallPositions = listOf("", "", "", "", "", "", "", "", "", "", "")
        )
        val goal = State(
            aRooms = listOf("A", "A", "A", "A"),
            bRooms = listOf("B", "B", "B", "B"),
            cRooms = listOf("C", "C", "C", "C"),
            dRooms = listOf("D", "D", "D", "D"),
            hallPositions = listOf("", "", "", "", "", "", "", "", "", "", "")
        )
        println(dijkstra(start, goal))
    }

    val input = readInput("Day23")
    part1(input)
    part2(input)
}
