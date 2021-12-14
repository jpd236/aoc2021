fun main() {
    fun run(input: List<String>, iterations: Int) {
        val str = input[0]
        val rules = input.subList(2, input.size).associate {
            val parts = it.split(" -> ")
            parts[0] to parts[1]
        }
        var bigramFreqs = mutableMapOf<String, Long>()
        str.indices.forEach { i ->
            if (i != str.indices.last) {
                val key = "${str[i]}${str[i + 1]}"
                bigramFreqs.inc(key, 1)
            }
        }
        repeat(iterations) {
            val newFreqs = mutableMapOf<String, Long>()
            bigramFreqs.entries.forEach { (key, value) ->
                val rule = rules[key]
                newFreqs.inc("${key[0]}$rule", value)
                newFreqs.inc("$rule${key[1]}", value)
            }
            bigramFreqs = newFreqs
        }
        val freqs = mutableMapOf<String, Long>()
        bigramFreqs.entries.forEach { (key, value) ->
            freqs.inc("${key[0]}", value)
            freqs.inc("${key[1]}", value)
        }
        freqs.inc("${str.first()}", 1)
        freqs.inc("${str.last()}", 1)
        println((freqs.values.maxOrNull()!! - freqs.values.minOrNull()!!) / 2)
    }

    fun part1(input: List<String>) {
        run(input, 10)
    }

    fun part2(input: List<String>) {
        run(input, 40)
    }

    val input = readInput("Day14")
    part1(input)
    part2(input)
}
