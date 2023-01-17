package tictactoe

var xWin = false
var oWin = false

fun main() {
    var player = '0'
    val grid = mutableListOf(
        mutableListOf(' ',' ',' '),
        mutableListOf(' ',' ',' '),
        mutableListOf(' ',' ',' ')
    )
    val turnedGrid = mutableListOf(
        mutableListOf(' ',' ',' '),
        mutableListOf(' ',' ',' '),
        mutableListOf(' ',' ',' ')
    )

    while (true) {

        when (player) {
            '0','O' -> player = 'X'
            'X' -> player = 'O'
        }

        drawGrid(grid)
        val chosenCell = readln()

        makeMove(grid, turnedGrid, chosenCell, player)

        checkGrid(grid)
        checkGrid(turnedGrid)
        checkDiagonal(arrayOf(grid[0][0],grid[1][1],grid[2][2]))
        checkDiagonal(arrayOf(grid[2][0],grid[1][1],grid[0][2]))

        if (getResult(grid) != "Game not finished") {
            drawGrid(grid)
            break
        }
    }

    println(getResult(grid))
}

fun makeMove(grid: MutableList<MutableList<Char>>, turnedGrid: MutableList<MutableList<Char>>, chosenCell: String, player: Char) {
    val cellYChar = chosenCell[0]
    val cellXChar = chosenCell[2]
    if (cellYChar.isDigit() && cellXChar.isDigit()) {
        val cellX = cellXChar.digitToInt()
        val cellY = cellYChar.digitToInt()
        if (cellX in 1..3 && cellY in 1..3) {
            if (grid[cellY-1][cellX-1] == ' ') {
                grid[cellY-1][cellX-1] = player
                turnedGrid[cellX-1][cellY-1] = player
            } else {
                println("This cell is occupied! Choose another one!")
                makeMove(grid, turnedGrid, readln(), player)
            }
        } else {
            println("Coordinates should be from 1 to 3!")
            makeMove(grid, turnedGrid, readln(), player)
        }
    } else {
        println("You should enter numbers!")
        makeMove(grid, turnedGrid, readln(), player)
    }
}

fun drawGrid(grid: MutableList<MutableList<Char>>) {
    println("""
        ---------
        | ${grid[0][0]} ${grid[0][1]} ${grid[0][2]} |
        | ${grid[1][0]} ${grid[1][1]} ${grid[1][2]} |
        | ${grid[2][0]} ${grid[2][1]} ${grid[2][2]} |
        ---------
    """.trimIndent())
}

fun checkGrid(grid: MutableList<MutableList<Char>>) {
    for (row in grid) {
        val firstValue = row[0]
        var allSame = true
        for (char in row) {
            if (char != firstValue) {
                allSame = false
            }
        }
        if (allSame) {
            if (firstValue == 'X') xWin = true
            if (firstValue == 'O') oWin = true
        }
    }
}

fun checkDiagonal(diagonal: Array<Char>) {
    val firstValue = diagonal[0]
    var allSame = true
    for (char in diagonal) {
        if (char != firstValue) allSame = false
    }
    if (allSame) {
        if (firstValue == 'X') xWin = true
        if (firstValue == 'O') oWin = true
    }
}

fun getResult(grid: MutableList<MutableList<Char>>): String {
    var amountX = 0
    var amountO = 0
    var amountEmpty = 0
    for (line in grid) {
        for (char in line) {
            if (char == 'X') amountX++
            if (char == 'O') amountO++
            if (char == ' ' || char == '_') amountEmpty++
        }
    }

    if (kotlin.math.abs(amountX - amountO)>1) return "Impossible"
    if (xWin && oWin) return "Impossible"

    if (!xWin && !oWin) {
        return if (amountEmpty > 0) {
            "Game not finished"
        } else {
            "Draw"
        }
    }

    if (xWin) {
        return "X wins"
    }

    return "O wins"
}