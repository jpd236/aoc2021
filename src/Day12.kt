fun main() {
    fun countPaths(graph: List<Pair<String, String>>, prefix: List<String>): Int {
        if (prefix.last() == "end") {
            return 1
        }
        val neighbors = graph.filter { (key, _) -> key == prefix.last() }.map { it.second }
            .filter { it.matches("[A-Z]+".toRegex()) || !prefix.contains(it) || it == "end" }
        return neighbors.sumOf {
            countPaths(graph, prefix + it)
        }
    }

    fun part1(input: List<String>) {
        val graph = mutableListOf<Pair<String, String>>()
        input.forEach { line ->
            val nodes = line.split("-")
            graph.add(nodes[0] to nodes[1])
            graph.add(nodes[1] to nodes[0])
        }
        println(countPaths(graph, listOf("start")))
    }

    fun countPaths2(graph: List<Pair<String, String>>, prefix: List<String>): Int {
        if (prefix.last() == "end") {
            return 1
        }
        val neighbors = graph.filter { (key, _) -> key == prefix.last() }.map { it.second }
            .filter { neighbor ->
                val hasSeenSmallTwice = prefix.freq().entries.any { (key, value) ->
                    key.matches("[a-z]+".toRegex()) && value == 2
                }
                neighbor.matches("[A-Z]+".toRegex())
                        || (hasSeenSmallTwice && !prefix.contains(neighbor))
                        || (!hasSeenSmallTwice)
                        || neighbor == "end"
            }
            .filterNot { it == "start" }
        return neighbors.sumOf {
            countPaths2(graph, prefix + it)
        }
    }

    fun part2(input: List<String>) {
        val graph = mutableListOf<Pair<String, String>>()
        input.forEach { line ->
            val nodes = line.split("-")
            graph.add(nodes[0] to nodes[1])
            graph.add(nodes[1] to nodes[0])
        }
        println(countPaths2(graph, listOf("start")))
    }

    val input = readInput("Day12")
    part1(input)
    part2(input)
}
