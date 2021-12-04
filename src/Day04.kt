fun main() {
    fun part1(input: List<String>) {
        val nums = input[0].split(",").map { it.toInt() }
        val boards = input.subList(2, input.size).filterNot { it == "" }.chunked(5).map { board ->
            board.map {
                it.trim().split("\\s+".toRegex()).map { num ->
                    num.toInt()
                }
            }
        }
        nums.indices.forEach { i ->
            boards.forEach { board ->
                val roundNums = nums.slice(0..i)
                board.indices.forEach { j ->
                    if (board[j].all { roundNums.contains(it) } || board.map { it[j] }.all { roundNums.contains(it)}) {
                        val unused = board.flatten().filterNot { roundNums.contains(it) }.sum()
                        println(unused * roundNums.last())
                        return
                    }
                }
            }
        }
    }

    fun part2(input: List<String>) {
        val nums = input[0].split(",").map { it.toInt() }
        val boards = input.subList(2, input.size).filterNot { it == "" }.chunked(5).map { board ->
            board.map {
                it.trim().split("\\s+".toRegex()).map {
                        num -> num.toInt()
                }
            }
        }
        val usedBoards = mutableSetOf<List<List<Int>>>()
        nums.indices.forEach { i ->
            boards.forEach { board ->
                if (!usedBoards.contains(board)) {
                    val roundNums = nums.slice(0..i)
                    board.indices.forEach { j ->
                        if (board[j].all { roundNums.contains(it) } ||
                            board.map { it[j] }.all { roundNums.contains(it)}
                        ) {
                            usedBoards.add(board)
                            if (usedBoards.size == boards.size) {
                                val unused = board.flatten().filterNot { roundNums.contains(it) }.sum()
                                println(unused * roundNums.last())
                                return
                            }
                        }
                    }
                }
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // part1(testInput)

    val input = readInput("Day04")
    part1(input)
    part2(input)
}
