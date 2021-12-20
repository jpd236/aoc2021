fun main() {
    fun enhance(input: List<String>, iters: Int) {
        val algo = input[0]
        val image = input.subList(2, input.size)
        var pixels = mutableSetOf<Pair<Int, Int>>()
        image.forEachIndexed { y, row ->
            row.forEachIndexed { x, ch ->
                if (ch == '#') pixels.add(x to y)
            }
        }
        repeat(iters) { iter ->
            val xMin = pixels.minOf { (x, _) -> x }
            val yMin = pixels.minOf { (_, y) -> y }
            val xMax = pixels.maxOf { (x, _) -> x }
            val yMax = pixels.maxOf { (_, y) -> y }
            val newPixels = mutableSetOf<Pair<Int, Int>>()
            ((yMin-2)..(yMax+2)).forEach { y ->
                ((xMin-2)..(xMax+2)).forEach { x ->
                    var num = ""
                    listOf(
                        -1 to -1, 0 to -1, 1 to -1,
                        -1 to 0, 0 to 0, 1 to 0,
                        -1 to 1, 0 to 1, 1 to 1
                    ).forEach { (i, j) ->
                        val isInMap = pixels.contains(x + i to y + j)
                        val outOfBounds = !(xMin..xMax).contains(x + i) || !(yMin..yMax).contains(y + j)
                        num += if (isInMap || (algo[0] == '#' && iter % 2 == 1 && outOfBounds)) "1" else "0"
                    }
                    val newValue = algo[num.toInt(2)]
                    if (newValue == '#') {
                        newPixels.add(x to y)
                    }
                }
            }
            pixels = newPixels
        }
        println(pixels.size)
    }

    fun part1(input: List<String>) {
        enhance(input, 2)
    }

    fun part2(input: List<String>) {
        enhance(input, 50)
    }

    val input = readInput("Day20")
    part1(input)
    part2(input)
}
