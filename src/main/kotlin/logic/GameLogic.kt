package tetrisSimple.logic

/**
 * Parse a string into a game field.
 *
 * Example of an input string:
 * ```
 * 3 4
 * .pp.
 * ....
 * .#..
 * ```
 */
fun parseField(inputStr: String): GameField{
    val lines = inputStr.lines()
    val (rows, columns) = try {
        lines[0].split(' ').map { it.toInt() }.also {
            if (it.size != 2) {
                throw RuntimeException(Messages.WRONG_INPUT_FORMAT)
            }
        }
    } catch (e: NumberFormatException) {
        throw RuntimeException(Messages.WRONG_INPUT_FORMAT)
    }

    val figure = mutableListOf<Point>()
    val landscape = mutableListOf<Point>()

    if (lines.size - 1 != rows) {
        throw RuntimeException(Messages.WRONG_INPUT_FORMAT)
    }

    for (i in 1 until lines.size) {
        val line = lines[i]
        if (line.length != columns) {
            throw RuntimeException(Messages.WRONG_INPUT_FORMAT)
        }
        for (j in line.indices) {
            when (line[j]) {
                '#' -> landscape.add(Point(i - 1, j))
                'p' -> figure.add(Point(i - 1, j))
                '.' -> {}
                else -> throw RuntimeException(Messages.WRONG_INPUT_FORMAT)
            }
        }
    }

    if (figure.isEmpty() || !isFigureConnected(figure)) {
        throw RuntimeException(Messages.WRONG_INPUT_FORMAT)
    }

    return GameField(rows, columns, figure, landscape)
}

/**
 * Check for figure connectivity using BFS.
 */
fun isFigureConnected(figure: List<Point>): Boolean {
    val visited = mutableSetOf<Point>()
    val queue = ArrayDeque<Point>()
    queue.add(figure[0]) // Start from the first point

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (visited.contains(current)) continue
        visited.add(current)

        // Add all the neighbors of the current point that are in figure
        val neighbors = listOf(
            Point(current.row - 1, current.column),        // upper neighbour
            Point(current.row + 1, current.column),        // lower neighbour
            Point(current.row, current.column - 1),     // left neighbour
            Point(current.row, current.column + 1)      // right neighbour
        )

        neighbors.filter { it in figure && it !in visited }.forEach { queue.add(it) }
    }

    return visited.size == figure.size
}

/**
 * Move the figure one row down, if possible.
 */
fun makeMoveDown(gameField: GameField): GameField {
    for (point in gameField.figure) {
        val newRow = point.row + 1
        if (newRow >= gameField.rows || gameField.landscape.any { it.row == newRow && it.column == point.column }) {
            return gameField
        }
    }

    gameField.figure = gameField.figure.map { point -> Point(point.row + 1, point.column) }

    return gameField
}

/**
 * Repeatedly move the figure down until it can no longer move.
 */
fun playGame(gameField: GameField, printEachStep: Boolean): String {
    val steps = StringBuilder()
    var currentGameField = gameField.copy().apply {
        this.figure = gameField.figure.map { it.copy() }.toMutableList()
    }
    var stepNumber = 0

    while (true) {
        if (printEachStep) {
            steps.append("STEP $stepNumber:\n${makeFieldToString(currentGameField)}\n\n")
        }

        var newGameField = currentGameField.copy().apply {
            this.figure = currentGameField.figure.map { it.copy() }.toMutableList()
        }
        newGameField = makeMoveDown(newGameField)
        if (newGameField.equals(currentGameField)) break
        currentGameField = newGameField
        stepNumber++
    }

    if (printEachStep) {
        return steps.toString().trimEnd('\n')
    }
    return makeFieldToString(currentGameField)
}

/**
 * Convert a game field into a multiline string.
 */
fun makeFieldToString(gameField: GameField): String {
    val resultOutput = Array(gameField.rows) { CharArray(gameField.columns) { '.' } }

    gameField.landscape.forEach { resultOutput[it.row][it.column] = '#' }
    gameField.figure.forEach { resultOutput[it.row][it.column] = 'p' }

    return resultOutput.joinToString("\n") { it.joinToString("") }
}

object Messages {
    val WRONG_INPUT_FORMAT = """
        Wrong input format
    """.trimIndent()
}