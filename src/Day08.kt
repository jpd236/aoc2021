fun main() {
    fun part1(input: List<String>) {
        println(input.sumOf { line ->
            val parts = line.split(" | ")
            val output = parts[1].split(" ")
            output.count { setOf(2, 4, 3, 7).contains(it.length) }
        })
    }

    fun processLine(line: String): Int {
        val parts = line.split(" | ")
        val inputs = parts[0].split(" ")
        val output = parts[1].split(" ")
        listOf(0, 1, 2, 3, 4, 5, 6).permute().forEach { perm ->
            val a = perm[0]
            val b = perm[1]
            val c = perm[2]
            val d = perm[3]
            val e = perm[4]
            val f = perm[5]
            val g = perm[6]

            fun digit(input: String) = when (input.map { it - 'a' }.toSet()) {
                setOf(a, b, c, e, f, g) -> 0
                setOf(c, f) -> 1
                setOf(a, c, d, e, g) -> 2
                setOf(a, c, d, f, g) -> 3
                setOf(b, c, d, f) -> 4
                setOf(a, b, d, f, g) -> 5
                setOf(a, b, d, e, f, g) -> 6
                setOf(a, c, f) -> 7
                setOf(a, b, c, d, e, f, g) -> 8
                setOf(a, b, c, d, f, g) -> 9
                else -> -1
            }

            if (inputs.map { digit(it) }.toSet() == setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)) {
                return digit(output[0]) * 1000 + digit(output[1]) * 100 + digit(output[2]) * 10 + digit(output[3])
            }
        }
        throw Exception()
    }

    fun part2(input: List<String>) {
        println(input.sumOf { line ->
            processLine(line)
        })
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day08_test")
    // part1(testInput)

    val input = readInput("Day08")
    part1(input)
    part2(input)
}
