fun main() {
    fun doFolds(input: List<String>, foldCount: Int? = null): Set<Pair<Int, Int>> {
        val splitPoint = input.indexOf("")
        var points = input.subList(0, splitPoint).map { row ->
            val parts = row.split(",")
            parts[0].toInt() to parts[1].toInt()
        }.toSet()
        val endIndex = if (foldCount == null) input.size else splitPoint + 1 + foldCount
        val folds = input.subList(splitPoint + 1, endIndex)
        folds.forEach { fold ->
            val parts = fold.substringAfter("fold along ").split("=")
            val axis = parts[0]
            val num = parts[1].toInt()
            if (axis == "x") {
                points = points.map { (x, y) ->
                    if (x > num) {
                        num - (x - num) to y
                    } else {
                        x to y
                    }
                }.toSet()
            } else {
                points = points.map { (x, y) ->
                    if (y > num) {
                        x to num - (y - num)
                    } else {
                        x to y
                    }
                }.toSet()
            }
        }
        return points
    }

    fun part1(input: List<String>) {
        val points = doFolds(input, 1)
        println(points.size)
    }

    fun part2(input: List<String>) {
        val points = doFolds(input)
        val xMax = points.maxOf { (x, _) -> x }
        val yMax = points.maxOf { (_, y) -> y }
        (0..yMax).forEach { y ->
            (0..xMax).forEach { x ->
                print(if (points.contains(x to y)) "#" else " ")
            }
            println()
        }
    }

    val input = readInput("Day13")
    part1(input)
    part2(input)
}
