package tetrisSimple.logic

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class GameLogicTest {

    @Test
    fun `parseField should parse field correctly and without problems`() {
        // Given
        val stringField = """
            5 6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When
        val gameField = parseField(stringField)

        // Then
        val expectedGameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(
                Point(2, 0), Point(2, 1), Point(2, 4), Point(2, 5),
                Point(3, 0), Point(3, 1), Point(3, 4), Point(3, 5),
                Point(4, 0), Point(4, 1), Point(4, 4), Point(4, 5)
            )
        )

        assertEquals(expectedGameField, gameField)
    }

    @Test
    fun `parseField should parse field with + figure correctly and without problems`() {
        // Given
        val stringField = """
            5 6
            ..p...
            .ppp..
            ..p...
            ##..##
            ##..##
        """.trimIndent()

        // When
        val gameField = parseField(stringField)

        // Then
        val expectedGameField = GameField(
            5,
            6,
            listOf(Point(0, 2),  Point(1, 1), Point(1, 2),
                Point(1, 3),  Point(2, 2)),
            listOf(
                Point(3, 0), Point(3, 1), Point(3, 4), Point(3, 5),
                Point(4, 0), Point(4, 1), Point(4, 4), Point(4, 5)
            )
        )

        assertEquals(expectedGameField, gameField)
    }

    @Test
    fun `parseField should throw an exception if too much expressions in first line`() {
        // Given
        val stringFieldWithTooManyExpressionsInFirstLine = """
            5 6 6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithTooManyExpressionsInFirstLine)
        }
    }

    @Test
    fun `parseField should throw an exception if no row count is provided`() {
        // Given
        val stringFieldWithNoRowCount = """
             6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithNoRowCount)
        }
    }

    @Test
    fun `parseField should throw an exception if no column count is provided`() {
        // Given
        val stringFieldWithNoColumnCount = """
            5 
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithNoColumnCount)
        }
    }

    @Test
    fun `parseField should throw an exception if no expression is provided in first line`() {
        // Given
        val stringFieldWithNoExpressionFirstLine = """
            
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithNoExpressionFirstLine)
        }
    }

    @Test
    fun `parseField should throw an exception if first line expressions is provided in second line`() {
        // Given
        val stringFieldWithFirstLineExpressionsInSecondLine = """
            
            5 6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithFirstLineExpressionsInSecondLine)
        }
    }

    @Test
    fun `parseField should throw an exception if first line is field`() {
        // Given
        val stringFieldWithFieldAsFirstLine = """
            ..p...
            5 6
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithFieldAsFirstLine)
        }
    }

    @Test
    fun `parseField should throw an exception if only field`() {
        // Given
        val stringFieldWithFieldOnly = """
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithFieldOnly)
        }
    }

    @Test
    fun `parseField should throw an exception if second line is not field`() {
        // Given
        val stringFieldWithNoFieldOnSecondLine = """
            5 6
            
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithNoFieldOnSecondLine)
        }
    }

    @Test
    fun `parseField should throw an exception if no field`() {
        // Given
        val stringFieldWithFieldOnly = """
            5 6
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithFieldOnly)
        }
    }

    @Test
    fun `parseField should throw an exception if field and expressions are reversed`() {
        // Given
        val stringFieldWithReversedThings = """
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
            5 6
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithReversedThings)
        }
    }

    @Test
    fun `parseField should throw an exception if rows are less than expected`() {
        // Given
        val stringFieldWithLessRowsAsExpected = """
            5 6
            ..p...
            ..pp..
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithLessRowsAsExpected)
        }
    }

    @Test
    fun `parseField should throw an exception if rows are bigger than expected`() {
        // Given
        val stringFieldWithMoreRowsAsExpected = """
            4 6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithMoreRowsAsExpected)
        }
    }

    @Test
    fun `parseField should throw an exception if columns are less than expected`() {
        // Given
        val stringFieldWithLessColumnsAsExpected = """
            5 6
            ..p...
            ..pp..
            ##..#
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithLessColumnsAsExpected)
        }
    }

    @Test
    fun `parseField should throw an exception if columns are bigger than expected`() {
        // Given
        val stringFieldWithMoreColumnsAsExpected = """
            5 5
            ..p..
            ..pp.
            ##..#
            ##..##
            ##..#
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithMoreColumnsAsExpected)
        }
    }

    @Test
    fun `parseField should throw an exception if row count is not a number`() {
        // Given
        val stringFieldWithRowsCountNotNumber = """
            a 6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithRowsCountNotNumber)
        }
    }

    @Test
    fun `parseField should throw an exception if column count is not a number`() {
        // Given
        val stringFieldWithColumnsCountNotNumber = """
            5 a
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithColumnsCountNotNumber)
        }
    }

    @Test
    fun `parseField should throw an exception if field has unpredicted chars`() {
        // Given
        val stringFieldWithFieldWithUnpredictedChars = """
            5 6
            ..p...
            ..pa..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithFieldWithUnpredictedChars)
        }
    }

    @Test
    fun `parseField should throw an exception if field has two figures`() {
        // Given
        val stringFieldWithTwoSymbols = """
            5 6
            ..p..p
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithTwoSymbols)
        }
    }

    @Test
    fun `parseField should throw an exception if field has two same figures`() {
        // Given
        val stringFieldWithTwoSameFigures = """
            5 6
            ..p..p
            ..p..p
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithTwoSameFigures)
        }
    }

    @Test
    fun `parseField should throw an exception if field has diagonal p`() {
        // Given
        val stringFieldWithDiagonalP = """
            5 6
            ..p...
            ...p..
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithDiagonalP)
        }
    }

    @Test
    fun `parseField should throw an exception if field has no figures`() {
        // Given
        val stringFieldWithNoFigures = """
            5 6
            ......
            ......
            ##..##
            ##..##
            ##..##
        """.trimIndent()

        // When, Then
        assertThrows<RuntimeException> {
            parseField(stringFieldWithNoFigures)
        }
    }

    @Test
    fun `makeMoveDown should move the figure one position down if there is no collision`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            emptyList()
        )

        // When
        val result = makeMoveDown(gameField)

        // Then
        val expectedGameField = GameField(
            5,
            6,
            listOf(Point(1, 2), Point(2, 2), Point(2, 3)),
            emptyList()
        )

        assertEquals(expectedGameField, result)
    }

    @Test
    fun `makeMoveDown should return the same game field if there is a collision`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(2, 3))
        )

        // When
        val result = makeMoveDown(gameField)

        // Then
        assertEquals(gameField, result)
    }

    @Test
    fun `makeMoveDown should return the same game field if the figure at the bottom of the field`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(3, 2), Point(4, 2), Point(4, 3)),
            emptyList()
        )

        // When
        val result = makeMoveDown(gameField)

        // Then
        assertEquals(gameField, result)
    }

    @Test
    fun `playGame should make the figure fall to the bottom`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            emptyList()
        )

        // When
        val result = playGame(gameField, false)

        // Then
        val expectedGameField = GameField(
            5,
            6,
            listOf(Point(3, 2), Point(4, 2), Point(4, 3)),
            emptyList()
        )

        assertEquals(makeFieldToString(expectedGameField), result)
    }

    @Test
    fun `playGame should make the figure fall before there is a collision`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(4, 3))
        )

        // When
        val result = playGame(gameField, false)

        // Then
        val expectedGameField = GameField(
            5,
            6,
            listOf(Point(2, 2), Point(3, 2), Point(3, 3)),
            listOf(Point(4, 3))
        )

        assertEquals(makeFieldToString(expectedGameField), result)
    }

    @Test
    fun `playGame should return the initial game field if there is a collision already`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(2, 3))
        )

        // When
        val result = playGame(gameField, false)

        // Then
        assertEquals(makeFieldToString(gameField), result)
    }

    @Test
    fun `playGame should return the initial game field if the figure at the bottom of the field`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(3, 2), Point(4, 2), Point(4, 3)),
            emptyList()
        )

        // When
        val result = playGame(gameField, false)

        // Then
        assertEquals(makeFieldToString(gameField), result)
    }

    @Test
    fun `playGame with printEachStep should make the figure fall to the bottom and show all steps`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            emptyList()
        )

        // When
        val result = playGame(gameField, true)

        // Then
        val expectedResult = """
            STEP 0:
            ..p...
            ..pp..
            ......
            ......
            ......

            STEP 1:
            ......
            ..p...
            ..pp..
            ......
            ......

            STEP 2:
            ......
            ......
            ..p...
            ..pp..
            ......

            STEP 3:
            ......
            ......
            ......
            ..p...
            ..pp..
        """.trimIndent()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `playGame with printEachStep should make the figure fall before there is a collision and show all steps`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(4, 3))
        )

        // When
        val result = playGame(gameField, true)

        // Then
        val expectedResult = """
            STEP 0:
            ..p...
            ..pp..
            ......
            ......
            ...#..

            STEP 1:
            ......
            ..p...
            ..pp..
            ......
            ...#..

            STEP 2:
            ......
            ......
            ..p...
            ..pp..
            ...#..
        """.trimIndent()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `playGame with printEachStep should return the initial game field if there is a collision already and show step 0`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(2, 3))
        )

        // When
        val result = playGame(gameField, true)

        // Then
        assertEquals("STEP 0:\n${makeFieldToString(gameField)}", result)
    }

    @Test
    fun `playGame with printEachStep should return the initial game field if the figure at the bottom of the field and show step 0`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(3, 2), Point(4, 2), Point(4, 3)),
            emptyList()
        )

        // When
        val result = playGame(gameField, true)

        // Then
        assertEquals("STEP 0:\n${makeFieldToString(gameField)}", result)
    }

    @Test
    fun `makeFieldToString should include figure in the output string`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            emptyList()
        )

        // When, Then
        val expectedOutput = "..p...\n..pp..\n......\n......\n......"

        assertEquals(expectedOutput, makeFieldToString(gameField))
    }

    @Test
    fun `makeFieldToString should include both landscape and figure in the output string if field has landscape`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(2, 3))
        )

        // When, Then
        val expectedOutput = "..p...\n..pp..\n...#..\n......\n......"

        assertEquals(expectedOutput, makeFieldToString(gameField))
    }

    @Test
    fun `makeFieldToString should return string with same amount of rows and columns as in the GameField object`() {
        // Given
        val gameField = GameField(
            5,
            6,
            listOf(Point(0, 2), Point(1, 2), Point(1, 3)),
            listOf(Point(2, 3))
        )

        // When
        val actualOutput = makeFieldToString(gameField)

        // Then
        val expectedOutput = "..p...\n..pp..\n...#..\n......\n......"

        assertEquals(expectedOutput, actualOutput)
        assertEquals(actualOutput.split('\n').size, gameField.rows)
        assertEquals(actualOutput.split('\n')[0].length, gameField.columns)
    }

    @Test
    fun `playGame should print the output string after figure drop down`() {
        // Given
        val stringField = """
            5 6
            ..p...
            ..pp..
            ##..##
            ##..##
            ##..##
        """.trimIndent()
        val gameField = parseField(stringField)

        // When
        val newGameField = playGame(gameField, false)

        // Then
        val expectedOutput = "......\n......\n##..##\n##p.##\n##pp##"

        assertEquals(expectedOutput, newGameField)
    }
}