import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

fun main() {
    fun getLine(pt1: Pair<Int, Int>, pt2: Pair<Int, Int>): List<Pair<Int, Int>> {
        val dx = (pt2.first - pt1.first).sign
        val dy = (pt2.second - pt1.second).sign
        val points = max(abs(pt1.first - pt2.first), abs(pt1.second - pt2.second))
        return (0..points).map { i ->
            (pt1.first + dx * i) to (pt1.second + dy * i)
        }
    }

    fun part1(input: List<String>) {
        val coveredPoints = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach { line ->
            val parts = line.split(" -> ").map {
                val coords = it.split(",")
                coords[0].toInt() to coords[1].toInt()
            }
            if (parts[0].first == parts[1].first || parts[0].second == parts[1].second) {
                getLine(parts[0], parts[1]).forEach { point ->
                    coveredPoints[point] = coveredPoints.getOrDefault(point, 0) + 1
                }
            }
        }
        println(coveredPoints.values.count { it > 1 })
    }

    fun part2(input: List<String>) {
        val coveredPoints = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach { line ->
            val parts = line.split(" -> ").map {
                val coords = it.split(",")
                coords[0].toInt() to coords[1].toInt()
            }
            getLine(parts[0], parts[1]).forEach { point ->
                coveredPoints[point] = coveredPoints.getOrDefault(point, 0) + 1
            }
        }
        println(coveredPoints.values.count { it > 1 })
    }

    val input = readInput("Day05")
    part1(input)
    part2(input)
}
