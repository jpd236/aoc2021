fun main() {
    fun part1(input: List<String>) {
        var hor = 0
        var ver = 0
        input.forEach { line ->
            val parts = line.split(" ")
            val cmd = parts[0]
            val amt = parts[1].toInt()
            when (cmd) {
                "forward" -> hor += amt
                "up" -> ver -= amt
                "down" -> ver += amt
            }
        }
        println(hor * ver)
    }

    fun part2(input: List<String>) {
        var hor = 0
        var ver = 0
        var aim = 0
        input.forEach { line ->
            val parts = line.split(" ")
            val cmd = parts[0]
            val amt = parts[1].toInt()
            when (cmd) {
                "forward" -> {
                    hor += amt
                    ver += amt * aim
                }
                "up" -> aim -= amt
                "down" -> aim += amt
            }
        }
        println(hor * ver)
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day02_test")
    // part1(testInput)

    val input = readInput("Day02")
    part1(input)
    part2(input)
}
