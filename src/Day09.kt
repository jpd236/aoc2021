fun main() {
    fun getLowPoints(input: List<String>): List<Pair<Int, Int>> {
        val lowPoints = mutableListOf<Pair<Int, Int>>()
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                val area = input[y][x].digitToInt()
                if (!listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1).any { (i, j) ->
                        i in input[0].indices && j in input.indices && input[j][i].digitToInt() <= area
                }) {
                    lowPoints.add(x to y)
                }
            }
        }
        return lowPoints
    }

    fun part1(input: List<String>) {
        println(getLowPoints(input).sumOf { (x, y) -> input[y][x].digitToInt() + 1 })
    }

    fun part2(input: List<String>) {
        val componentSizes = mutableListOf<Int>()
        getLowPoints(input).forEach { point ->
            val stack = mutableListOf(point)
            val visited = mutableSetOf(point)
            while (stack.isNotEmpty()) {
                val pt = stack.removeFirst()
                stack.remove(pt)
                listOf(
                    pt.first - 1 to pt.second,
                    pt.first + 1 to pt.second,
                    pt.first to pt.second - 1,
                    pt.first to pt.second + 1
                ).forEach { (x, y) ->
                    if (x in input[0].indices && y in input.indices
                        && input[y][x].digitToInt() != 9
                        && !visited.contains(x to y)
                    ) {
                        visited.add(x to y)
                        stack.add(x to y)
                    }
                }
            }
            componentSizes += visited.size
        }
        val sorted = componentSizes.sortedDescending()
        println(sorted[0] * sorted[1] * sorted[2])
    }

    val input = readInput("Day09")
    part1(input)
    part2(input)
}
