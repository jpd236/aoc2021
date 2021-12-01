fun main() {
    fun part1(input: List<String>) {
        println(input.fold(0 to Int.MAX_VALUE) { (increases, last), value ->
            (if (value.toInt() > last) increases + 1 else increases) to value.toInt()
        }.first)
    }

    fun part2(input: List<String>) {
        println(input.fold(0 to listOf<Int>()) { (increases, prev), value ->
            if (prev.size < 3) {
                increases to prev + value.toInt()
            } else {
                val curSum = prev.sum()
                val nextList = prev.slice(1..2) + value.toInt()
                val nextSum = nextList.sum()
                (if (nextSum > curSum) increases + 1 else increases) to nextList
            }
        }.first)
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // part1(testInput)

    val input = readInput("Day01")
    part1(input)
    part2(input)
}
