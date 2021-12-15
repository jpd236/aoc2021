import java.util.PriorityQueue

fun main() {
    data class Node(val point: Pair<Int, Int>, val cost: Long): Comparable<Node> {
        override fun compareTo(other: Node): Int {
            return compareValues(cost, other.cost)
        }
    }

    fun dijkstra(input: List<List<Int>>): Long {
        val destination = input[0].indices.last to input.indices.last
        val queue = PriorityQueue<Node>()
        val visited = mutableSetOf<Pair<Int, Int>>()
        queue.add(Node(0 to 0, 0))
        while (queue.isNotEmpty() && queue.peek().point != destination) {
            val min = queue.poll()
            listOf(
                min.point.first - 1 to min.point.second,
                min.point.first + 1 to min.point.second,
                min.point.first to min.point.second - 1,
                min.point.first to min.point.second + 1,
            ).filter { (x, y) ->
                x in input[0].indices && y in input.indices
            }.filterNot {
                visited.contains(it)
            }.forEach { (x, y) ->
                visited.add(x to y)
                queue.add(Node(x to y, min.cost + input[y][x]))
            }
        }
        return queue.peek().cost
    }

    fun part1(input: List<String>) {
        println(dijkstra(input.map { row -> row.map { it.digitToInt() }}))
    }

    fun part2(input: List<String>) {
        val newInput = (0 until input.size * 5).map { y ->
            (0 until input[0].length * 5).map { x ->
                val yMult = y / input.size
                val xMult = x / input[0].length
                val yOrig = y % input.size
                val xOrig = x % input[0].length
                ((input[yOrig][xOrig].digitToInt() + xMult + yMult - 1) % 9) + 1
            }
        }
        println(dijkstra(newInput))
    }

    val input = readInput("Day15")
    part1(input)
    part2(input)
}
