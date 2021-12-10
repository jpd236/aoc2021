fun main() {
    fun processLine(line: String): Int {
        val map = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
        val errors = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        val stack = mutableListOf<Char>()
        line.forEach { ch ->
            if (map.contains(ch)) {
                stack.add(ch)
            } else {
                if (stack.isEmpty() || ch != map[stack.last()]) {
                    return errors[ch]!!
                } else {
                    stack.removeLast()
                }
            }
        }
        return 0
    }

    fun processLine2(line: String): Long {
        val map = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
        val values = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
        val stack = mutableListOf<Char>()
        line.forEach { ch ->
            if (setOf('(', '[', '{', '<').contains(ch)) {
                stack.add(ch)
            } else {
                stack.removeLast()
            }
        }
        return stack.reversed().fold(0L) { acc, ch ->
            acc * 5 + values[map[ch]!!]!!
        }
    }

    fun part1(input: List<String>) {
        println(input.sumOf { line ->
            processLine(line)
        })
    }

    fun part2(input: List<String>) {
        val list = input.filterNot { processLine(it) > 0 }.map { processLine2(it) }.sorted()
        println(list[list.size / 2])
    }

    val input = readInput("Day10")
    part1(input)
    part2(input)
}
