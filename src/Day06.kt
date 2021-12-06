import java.math.BigInteger

fun main() {
    fun day6(input: String, iterations: Int) {
        val ages = input.split(",").map { it.toInt() }
        val sim = mutableMapOf<Int, BigInteger>()
        ages.forEach { age ->
            sim[age] = sim.getOrDefault(age, BigInteger.ZERO).plus(BigInteger.ONE)
        }
        repeat(iterations) {
            val zeros = sim.getOrDefault(0, BigInteger.ZERO).toLong()
            (1..8).forEach { age ->
                sim[age - 1] = sim.getOrDefault(age, BigInteger.ZERO)
            }
            sim[6] = sim.getOrDefault(6, BigInteger.ZERO).plus(BigInteger.valueOf(zeros))
            sim[8] = BigInteger.valueOf(zeros)
        }
        println(sim.values.fold(BigInteger.ZERO) {total, sum ->
            total.plus(sum)
        })
    }

    fun part1(input: List<String>) {
        day6(input[0], 80)
    }

    fun part2(input: List<String>) {
        day6(input[0], 256)
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day06_test")
    // part1(testInput)

    val input = readInput("Day06")
    part1(input)
    part2(input)
}
