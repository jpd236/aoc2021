import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun String.intList(): List<Int> = split(",").map { it.toInt() }

fun List<Int>.median(): Double {
    val sorted = sorted()
    if (sorted.size % 2 == 1) {
        return sorted[sorted.size / 2].toDouble()
    } else {
        return (sorted[sorted.size / 2 - 1] + sorted[sorted.size / 2]) / 2.toDouble()
    }
}