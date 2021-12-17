fun main() {
    fun runRockets(input: List<String>): List<Int> {
        val parts = input[0].split(": ")[1].split(", ").associate { coords ->
            val coordParts = coords.split("=")
            val rangeParts = coordParts[1].split("..")
            coordParts[0] to rangeParts[0].toInt()..rangeParts[1].toInt()
        }
        val maxYs = mutableListOf<Int>()
        (-1000..1000).forEach { xv ->
            (-1000..1000).forEach { yv ->
                var maxYIter = 0
                var x = 0
                var y = 0
                var i = xv
                var j = yv
                var hit = false
                repeat(1000) {
                    x += i
                    y += j
                    i = if (i > 0) i - 1 else if (i < 0) i + 1 else 0
                    j -= 1
                    if (y > maxYIter) {
                        maxYIter = y
                    }
                    if (x in parts["x"]!! && y in parts["y"]!!) {
                        hit = true
                    }
                }
                if (hit) {
                    maxYs.add(maxYIter)
                }
            }
        }
        return maxYs
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day17_test")
    // part1(testInput)

    val input = readInput("Day17")
    val hits = runRockets(input)
    println(hits.maxOrNull()!!)
    println(hits.size)
}
