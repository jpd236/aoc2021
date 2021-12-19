import kotlin.math.abs
import kotlin.math.max

data class Point(val x: Int, val y: Int, val z: Int)

fun main() {
    fun run(input: List<String>) {
        val scanners = input.joinToString("\n").split("\n\n").map {
            it.split("\n")
        }.map {
            it.subList(1, it.size).map { row ->
                val parts = row.split(",")
                Point(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            }.toMutableSet()
        }.toList()

        val translations = mutableMapOf<Int, Point>()
        translations[0] = Point(0, 0, 0)
        val mergedScanners = mutableSetOf<Int>()
        fun mergeScanners() {
            scanners.forEachIndexed { i, s ->
                if (i != 0 && !mergedScanners.contains(i)) {
                    listOf(
                        { (x, y, z): Point -> Point(x, y, z) },
                        { (x, y, z): Point -> Point(x, -y, -z) },
                        { (x, y, z): Point -> Point(-x, y, -z) },
                        { (x, y, z): Point -> Point(-x, -y, z) },
                        { (x, y, z): Point -> Point(x, z, -y) },
                        { (x, y, z): Point -> Point(x, -z, y) },
                        { (x, y, z): Point -> Point(-x, z, y) },
                        { (x, y, z): Point -> Point(-x, -z, -y) },
                        { (x, y, z): Point -> Point(y, x, -z) },
                        { (x, y, z): Point -> Point(y, -x, z) },
                        { (x, y, z): Point -> Point(-y, x, z) },
                        { (x, y, z): Point -> Point(-y, -x, -z) },
                        { (x, y, z): Point -> Point(y, z, x) },
                        { (x, y, z): Point -> Point(y, -z, -x) },
                        { (x, y, z): Point -> Point(-y, z, -x) },
                        { (x, y, z): Point -> Point(-y, -z, x) },
                        { (x, y, z): Point -> Point(z, y, -x) },
                        { (x, y, z): Point -> Point(z, -y, x) },
                        { (x, y, z): Point -> Point(-z, y, x) },
                        { (x, y, z): Point -> Point(-z, -y, -x) },
                        { (x, y, z): Point -> Point(z, x, y) },
                        { (x, y, z): Point -> Point(z, -x, -y) },
                        { (x, y, z): Point -> Point(-z, x, -y) },
                        { (x, y, z): Point -> Point(-z, -x, y) },
                    ).forEach { transform ->
                        val sRotated = s.map(transform)
                        scanners[0].forEach { p1 ->
                            sRotated.forEach { p2 ->
                                val sTranslated = sRotated.map { (x, y, z) ->
                                    Point(
                                        x + (p1.x - p2.x),
                                        y + (p1.y - p2.y),
                                        z + (p1.z - p2.z),
                                    )
                                }
                                if (sTranslated.intersect(scanners[0]).size >= 12) {
                                    translations[i] = Point(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z)
                                    mergedScanners.add(i)
                                    scanners[0].addAll(sTranslated)
                                    return
                                }
                            }
                        }
                    }
                }
            }
            error("No scanners to merge")
        }

        while (mergedScanners.size < scanners.size - 1) {
            mergeScanners()
        }

        var maxDist = Int.MIN_VALUE
        scanners.indices.forEach { i ->
            scanners.indices.forEach { j ->
                val from = translations[i]!!
                val to = translations[j]!!
                val dist = abs(from.x - to.x) + abs(from.y - to.y) + abs(from.z - to.z)
                maxDist = max(dist, maxDist)
            }
        }

        println(scanners[0].size)
        println(maxDist)
    }

    val input = readInput("Day19")
    run(input)
}
