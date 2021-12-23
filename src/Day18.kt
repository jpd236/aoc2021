import kotlin.math.max

sealed class Day18Node {
    override fun toString(): String {
        return when (this) {
            is NumberNode -> value.toString()
            is PairNode -> "[$left,$right]"
        }
    }
}
class NumberNode(var value: Int) : Day18Node()
class PairNode(var left: Day18Node, var right: Day18Node): Day18Node()

fun main() {
    fun parseTree(input: String): Pair<Day18Node, String> {
        return if (input.startsWith('[')) {
            val leftResult = parseTree(input.substring(1))
            val rightResult = parseTree(leftResult.second.substring((1)))
            PairNode(leftResult.first, rightResult.first) to rightResult.second.substring((1))
        } else {
            val result = "[0-9]+".toRegex().find(input)!!.value
            NumberNode(result.toInt()) to input.substring(result.length)
        }
    }

    fun explode(node: Day18Node): Boolean {
        var depth = 0
        var prevNumber: NumberNode? = null
        var numberToAdd: Int? = null
        var curParent: PairNode? = null
        var curIsLeft = true
        fun traverse(node: Day18Node): Boolean {
            when (node) {
                is NumberNode -> {
                    if (numberToAdd != null) {
                        node.value += numberToAdd!!
                        return false
                    }
                    prevNumber = node
                    return true
                }
                is PairNode -> {
                    if (depth == 4 && numberToAdd == null) {
                        require(node.left is NumberNode && node.right is NumberNode)
                        if (prevNumber != null) {
                            prevNumber!!.value += (node.left as NumberNode).value
                        }
                        numberToAdd = (node.right as NumberNode).value
                        if (curIsLeft) {
                            curParent!!.left = NumberNode(0)
                        } else {
                            curParent!!.right = NumberNode(0)
                        }
                        return true
                    }
                    depth++
                    curIsLeft = true
                    val prevParent = curParent
                    curParent = node
                    if (!traverse(node.left)) return false
                    curIsLeft = false
                    if (!traverse(node.right)) return false
                    curParent = prevParent
                    depth--
                    return true
                }
            }
        }
        traverse(node)
        return numberToAdd != null
    }

    fun split(node: Day18Node): Boolean {
        var curParent: PairNode? = null
        var curIsLeft = true
        var found = false
        fun traverse(node: Day18Node) {
            when (node) {
                is NumberNode -> {
                    if (node.value >= 10 && !found) {
                        val left = NumberNode(node.value / 2)
                        val right = NumberNode(node.value - left.value)
                        if (curIsLeft) {
                            curParent!!.left = PairNode(left, right)
                        } else {
                            curParent!!.right = PairNode(left, right)
                        }
                        found = true
                    }
                }
                is PairNode -> {
                    curIsLeft = true
                    val prevParent = curParent
                    curParent = node
                    traverse(node.left)
                    curIsLeft = false
                    traverse(node.right)
                    curParent = prevParent
                }
            }
        }
        traverse(node)
        return found
    }

    fun magnitude(node: Day18Node): Long {
        return when (node) {
            is NumberNode -> node.value.toLong()
            is PairNode -> 3 * magnitude(node.left) + 2 * magnitude(node.right)
        }
    }

    fun combine(node1: Day18Node, node2: Day18Node): Day18Node {
        val result = PairNode(node1, node2)
        do {
            var hasReduction = explode(result)
            if (!hasReduction) {
                hasReduction = split(result)
            }
        } while (hasReduction)
        return result
    }

    fun part1(input: List<String>) {
        println(magnitude(input.map { parseTree(it).first }.reduce { sum, row ->
            combine(sum, row)
        }))
    }

    fun part2(input: List<String>) {
        var maxMagnitude = Long.MIN_VALUE
        input.forEachIndexed { i, node1 ->
            input.forEachIndexed { j, node2 ->
                if (i != j) {
                    maxMagnitude = max(maxMagnitude, magnitude(combine(parseTree(node1).first, parseTree(node2).first)))
                }
            }
        }
        println(maxMagnitude)
    }

    val input = readInput("Day18")
    part1(input)
    part2(input)
}
