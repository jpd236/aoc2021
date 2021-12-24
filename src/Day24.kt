import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("Day24")
    val params = input.chunked(18).map { chunk ->
        // Line 5: div z {param1}, either 1 or 26
        val param1 = chunk[4].substringAfterLast(" ").toInt()
        // Line 6: add x {param2}
        val param2 = chunk[5].substringAfterLast(" ").toInt()
        // Line 16: add y {param3}
        val param3 = chunk[15].substringAfterLast(" ").toInt()
        Triple(param1, param2, param3)
    }

    fun runChunk(z: Int, w: Int, chunkParams: Triple<Int, Int, Int>): Int {
        return if (z % 26 + chunkParams.second == w) {
            z / chunkParams.first
        } else {
            26 * z / chunkParams.first + w + chunkParams.third
        }
    }

    // Map from current z value to the smallest and largest w strings that result in that value.
    var zValues = mapOf(0 to (0L to 0L))
    params.forEach { chunkParams ->
        val newZValues = mutableMapOf<Int, Pair<Long, Long>>()
        zValues.forEach { z, (minW, maxW) ->
            for (w in 1..9) {
                val newZ = runChunk(z, w, chunkParams)
                // Passes which divide by 26 must shrink the z value
                if (chunkParams.first == 26 && newZ > z) continue
                val curMinMax = newZValues.getOrDefault(newZ, Long.MAX_VALUE to Long.MIN_VALUE)
                newZValues[newZ] = min(curMinMax.first, 10 * minW + w) to max(curMinMax.second, 10 * maxW + w)
            }
        }
        zValues = newZValues
    }
    println(zValues[0]!!.second)
    println(zValues[0]!!.first)
}
