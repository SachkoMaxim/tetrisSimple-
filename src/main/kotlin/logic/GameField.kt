package tetrisSimple.logic

data class GameField(
    val rows: Int,
    val columns: Int,
    var figure: List<Point>,
    val landscape: List<Point>
)