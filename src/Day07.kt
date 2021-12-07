import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) {
        val nums = input[0].intList()
        val min = nums.minOf { it }
        val max = nums.maxOf { it }
        println((min..max).minOfOrNull { pt ->
            nums.sumOf { abs(pt - it) }
        })
    }

    fun part2(input: List<String>) {
        val nums = input[0].intList()
        val min = nums.minOf { it }
        val max = nums.maxOf { it }
        println((min..max).minOfOrNull { pt ->
            nums.sumOf {
                val tmp = abs(pt - it)
                tmp * (tmp + 1) / 2
            }
        })
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day07_test")
    // part1(testInput)

    val input = readInput("Day07")
    part1(input)
    part2(input)
}
