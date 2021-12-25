fun main() {
    fun part1(input: List<String>) {
        var moves = 0
        var board = input
        do {
            var hasMove = false
            var newBoard = board.map { it.toMutableList() }
            board.forEachIndexed { y, row ->
                row.forEachIndexed { x, ch ->
                    if (ch == '>' && board[y][(x + 1) % input[y].length] == '.') {
                        newBoard[y][(x + 1) % input[y].length] = '>'
                        newBoard[y][x] = '.'
                        hasMove = true
                    }
                }
            }
            board = newBoard.map { it.joinToString("") }
            newBoard = board.map { it.toMutableList() }
            board.forEachIndexed { y, row ->
                row.forEachIndexed { x, ch ->
                    if (ch == 'v' && board[(y + 1) % input.size][x] == '.') {
                        newBoard[(y + 1) % input.size][x] = 'v'
                        newBoard[y][x] = '.'
                        hasMove = true
                    }
                }
            }
            board = newBoard.map { it.joinToString("") }
            moves++
        } while (hasMove)
        println(moves)
    }

    val input = readInput("Day25")
    part1(input)
}
