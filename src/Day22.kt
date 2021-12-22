import kotlin.math.max
import kotlin.math.min

fun main() {
    fun calculateArea(input: List<String>, filterLarge: Boolean) {
        val tracker = mutableMapOf<Pair<Point, Point>, Int>()
        input.forEach { line ->
            val parts = line.split(" ")
            val isOn = parts[0] == "on"
            val ranges = parts[1].split(",").associate { range ->
                val rangeParts = range.split("=")
                val axis = rangeParts[0]
                val from = rangeParts[1].substringBefore("..").toInt()
                val to = rangeParts[1].substringAfter("..").toInt()
                axis to from..to
            }
            if (filterLarge && (ranges["x"]!!.first < -50 || ranges["x"]!!.first > 50)) {
                return@forEach
            }
            tracker.toMap().entries.forEach { (existingPoint, existingSign) ->
                val intersectionX1 = max(existingPoint.first.x, ranges["x"]!!.first)
                val intersectionX2 = min(existingPoint.second.x, ranges["x"]!!.last)
                val intersectionY1 = max(existingPoint.first.y, ranges["y"]!!.first)
                val intersectionY2 = min(existingPoint.second.y, ranges["y"]!!.last)
                val intersectionZ1 = max(existingPoint.first.z, ranges["z"]!!.first)
                val intersectionZ2 = min(existingPoint.second.z, ranges["z"]!!.last)
                if (
                    intersectionX1 <= intersectionX2 &&
                    intersectionY1 <= intersectionY2 &&
                    intersectionZ1 <= intersectionZ2
                ) {
                    val intersectionP1 = Point(intersectionX1, intersectionY1, intersectionZ1)
                    val intersectionP2 = Point(intersectionX2, intersectionY2, intersectionZ2)
                    tracker.inc(intersectionP1 to intersectionP2, -existingSign)
                }
            }
            if (isOn) {
                val newP1 = Point(ranges["x"]!!.first, ranges["y"]!!.first, ranges["z"]!!.first)
                val newP2 = Point(ranges["x"]!!.last, ranges["y"]!!.last, ranges["z"]!!.last)
                tracker.inc(newP1 to newP2, 1)
            }
        }
        println(tracker.entries.sumOf { (cube, sign) ->
            (cube.second.x - cube.first.x + 1L) *
                    (cube.second.y - cube.first.y + 1L) *
                    (cube.second.z - cube.first.z + 1L) *
                    sign
        })
    }

    fun part1(input: List<String>) {
        calculateArea(input, filterLarge = true)
    }

    fun part2(input: List<String>) {
        calculateArea(input, filterLarge = false)
    }

    val input = readInput("Day22")
    part1(input)
    part2(input)
}
