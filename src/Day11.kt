fun main() {
    fun getFlashes(levels: List<List<Int>>): Set<Pair<Int, Int>> {
        return levels.flatMapIndexed { y, row ->
            row.mapIndexed { x, lev ->
                (x to y) to lev
            }
        }.filter { it.second > 9 }.map { it.first }.toSet()
    }

    fun runIter(levels: List<MutableList<Int>>): Int {
        levels.indices.forEach { y ->
            levels[y].indices.forEach { x ->
                levels[y][x]++
            }
        }
        val flashes = mutableSetOf<Pair<Int, Int>>()
        while (getFlashes(levels) != flashes) {
            val iterFlashes = getFlashes(levels).minus(flashes)
            iterFlashes.forEach { (x, y) ->
                listOf(
                    x - 1 to y - 1,
                    x to y - 1,
                    x + 1 to y - 1,
                    x - 1 to y,
                    x + 1 to y,
                    x - 1 to y + 1,
                    x to y + 1,
                    x + 1 to y + 1
                ).forEach { (i, j) ->
                    if (i in levels[0].indices && j in levels.indices) {
                        levels[j][i]++
                    }
                }
                flashes.add(x to y)
            }
        }
        flashes.forEach { (x, y) ->
            levels[y][x] = 0
        }
        return flashes.size
    }

    fun part1(input: List<String>) {
        val levels = input.map { row -> row.map { ch -> ch.digitToInt() }.toMutableList() }
        var totalFlashes = 0
        repeat (100) {
            totalFlashes += runIter(levels)
        }
        println(totalFlashes)
    }

    fun part2(input: List<String>) {
        val levels = input.map { row -> row.map { ch -> ch.digitToInt() }.toMutableList() }
        var iters = 0
        while (levels.flatten().filterNot { it == 0 }.isNotEmpty()) {
            runIter(levels)
            iters++
        }
        println(iters)
    }

    val input = readInput("Day11")
    part1(input)
    part2(input)
}
