import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>) {
        var p1Score = 0
        var p2Score = 0
        var p1Pos = input[0].substringAfter(": ").toInt()
        var p2Pos = input[1].substringAfter(": ").toInt()
        var turn = 0
        while (p1Score < 1000 && p2Score < 1000) {
            repeat(3) {
                p1Pos = (p1Pos + (++turn - 1) % 100) % 10 + 1
            }
            p1Score += p1Pos
            if (p1Score >= 1000) break
            repeat(3) {
                p2Pos = (p2Pos + (++turn - 1) % 100 + 1 - 1) % 10 + 1
            }
            p2Score += p2Pos
        }
        println(min(p1Score, p2Score) * turn)
    }

    data class State(
        val p1Score: Int,
        val p2Score: Int,
        val p1Pos: Int,
        val p2Pos: Int,
        val rollNum: Int,
    )

    fun part2(input: List<String>) {
        val resultCache = mutableMapOf<State, Pair<Long, Long>>()
        fun calcUniverses(state: State): Pair<Long, Long> {
            if (resultCache.containsKey(state)) return resultCache[state]!!
            if (state.p1Score >= 21) return 1L to 0L
            if (state.p2Score >= 21) return 0L to 1L
            var p1Universes = 0L
            var p2Universes = 0L
            if (state.rollNum % 6 < 3) {
                (1..3).forEach { roll ->
                    val newPos = (state.p1Pos + roll - 1) % 10 + 1
                    val newScore = state.p1Score + (if (state.rollNum % 6 == 2) newPos else 0)
                    val universes = calcUniverses(
                        state.copy(
                            p1Pos = newPos,
                            p1Score = newScore,
                            rollNum = state.rollNum + 1 % 6
                        )
                    )
                    p1Universes += universes.first
                    p2Universes += universes.second
                }
            } else {
                (1..3).forEach { roll ->
                    val newPos = (state.p2Pos + roll - 1) % 10 + 1
                    val newScore = state.p2Score + (if (state.rollNum % 6 == 5) newPos else 0)
                    val universes = calcUniverses(
                        state.copy(
                            p2Pos = newPos,
                            p2Score = newScore,
                            rollNum = state.rollNum + 1 % 6
                        )
                    )
                    p1Universes += universes.first
                    p2Universes += universes.second
                }
            }
            resultCache[state] = p1Universes to p2Universes
            return resultCache[state]!!
        }
        val universes = calcUniverses(State(
            p1Score = 0,
            p2Score = 0,
            p1Pos = input[0].substringAfter(": ").toInt(),
            p2Pos = input[1].substringAfter(": ").toInt(),
            rollNum = 0
        ))
        println(max(universes.first, universes.second))
    }

    val input = readInput("Day21")
    part1(input)
    part2(input)
}
