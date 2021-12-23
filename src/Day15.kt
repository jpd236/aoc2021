fun main() {
    fun getNeighbors(graph: List<List<Int>>, point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            point.first - 1 to point.second,
            point.first + 1 to point.second,
            point.first to point.second - 1,
            point.first to point.second + 1,
        ).filter { (x, y) ->
            x in graph[0].indices && y in graph.indices
        }
    }

    fun part1(input: List<String>) {
        val graph = input.map { row -> row.map { it.digitToInt() }}
        data class Point(val point: Pair<Int, Int>): Node<Point> {
            override fun getNeighbors(): List<Pair<Point, Long>> {
                return getNeighbors(graph, point)
                    .map { point -> Point(point) to graph[point.second][point.first].toLong() }
            }
        }
        println(dijkstra(Point(0 to 0), Point(graph[0].lastIndex to graph.lastIndex)))
    }

    fun part2(input: List<String>) {
        val graph = (0 until input.size * 5).map { y ->
            (0 until input[0].length * 5).map { x ->
                val yMult = y / input.size
                val xMult = x / input[0].length
                val yOrig = y % input.size
                val xOrig = x % input[0].length
                ((input[yOrig][xOrig].digitToInt() + xMult + yMult - 1) % 9) + 1
            }
        }
        data class Point(val point: Pair<Int, Int>): Node<Point> {
            override fun getNeighbors(): List<Pair<Point, Long>> {
                return getNeighbors(graph, point)
                    .map { point -> Point(point) to graph[point.second][point.first].toLong() }
            }
        }
        println(dijkstra(Point(0 to 0), Point(graph[0].lastIndex to graph.lastIndex)))
    }

    val input = readInput("Day15")
    part1(input)
    part2(input)
}
