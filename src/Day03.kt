import java.math.BigInteger

fun main() {
    fun part1(input: List<String>) {
        val zeros = mutableMapOf<Int, Int>()
        val ones = mutableMapOf<Int, Int>()
        input.forEach { line ->
            line.forEachIndexed { i, ch ->
                val map = if (ch == '0') zeros else ones
                map[i] = map.getOrDefault(i, 0) + 1
            }
        }
        var gamma = ""
        var epsilon = ""
        zeros.keys.sorted().forEach { i ->
            if (zeros[i]!! > ones[i]!!) {
                gamma += "0"
                epsilon += "1"
            } else {
                gamma += "1"
                epsilon += "0"
            }
        }
        println(BigInteger(gamma, 2).times(BigInteger(epsilon, 2)))
    }

    fun part2(input: List<String>) {
        var remainingMost = input
        var i = 0
        while (remainingMost.size > 1) {
            var zeros = 0
            var ones = 0
            remainingMost.forEach { line ->
                if (line[i] == '0') {
                    zeros++
                } else {
                    ones++
                }
            }
            remainingMost = remainingMost.filter {
                it[i] == if (zeros > ones) '0' else '1'
            }
            i++
        }

        var remainingLeast = input
        i = 0
        while (remainingLeast.size > 1) {
            var zeros = 0
            var ones = 0
            remainingLeast.forEach { line ->
                if (line[i] == '0') {
                    zeros++
                } else {
                    ones++
                }
            }
            remainingLeast = remainingLeast.filter {
                it[i] == if (zeros > ones) '1' else '0'
            }
            i++
        }
        println(BigInteger(remainingMost[0], 2).times(BigInteger(remainingLeast[0], 2)))
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day03_test")
    // part1(testInput)

    val input = readInput("Day03")
    part1(input)
    part2(input)
}
