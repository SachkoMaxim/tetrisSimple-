package tetrisSimple

import tetrisSimple.io.MainInput
import tetrisSimple.io.MainOutput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals

class MainKtTest {

    // Mock
    val messages = mutableListOf<String>()
    private val outputMock: MainOutput = object : MainOutput {
        override fun printLine(msg: String) {
            messages.add(msg)
        }
    }

    private val defaultInputMock: MainInput = object : MainInput {
        override fun ifFileExist(filePath: String): Boolean {
            throw RuntimeException("isFileExist has been called unexpectedly")
        }

        override fun readFileAsString(filePath: String): String {
            throw RuntimeException("readFileAsString has been called unexpectedly")
        }
    }

    @BeforeEach
    fun setUp() {
        messages.clear()
    }

    @Test
    fun `it should show a message with How-to-use instructions if there is no input file arg`() {
        // When
        mainHandler(emptyArray(), outputMock, defaultInputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.NO_ARGS, messages[0])
    }

    @Test
    fun `it should check if file does exist and show a message if not`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = false

            override fun readFileAsString(filePath: String): String {
                throw RuntimeException("readFileAsString has been called unexpectedly")
            }
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_DOES_NOT_EXIST, messages[0])
    }

    @Test
    fun `it should parse input file and throw an error if it is invalid`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = "Wrong input file body"
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input is empty`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = ""
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains too much expressions in first line`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains only column count`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains only row count`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains no expressions in first line`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """

                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains expressions on second line`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """

                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains field on first line`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                ..p.
                6 4
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains field on third line`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains only field`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains no field`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input is inverted`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
                6 4
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains less rows than expected`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains more rows than expected`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                5 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains less columns than expected`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                .pp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains more columns than expected`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 3
                ..p
                .pp
                ..p
                #...
                ...
                ...
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains unexpected symbols for row count`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                a 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains unexpected symbols for column count`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 a
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains unexpected symbols`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                .pap
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains no figure`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ....
                ....
                ....
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains two figures`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                ....
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains two same figures`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ....
                .ppp
                ....
                #ppp
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show error message if input contains diagonal p`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ....
                ...p
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(Messages.INPUT_FILE_HAS_WRONG_CONTENT, messages[0])
    }

    @Test
    fun `it should show result field state with figure on the bottom if there are no collision`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)

        val expectedResultField = """
            ....
            ....
            ....
            #.p.
            .ppp
            ..p#
        """.trimIndent()
        assertEquals(expectedResultField, messages[0])
    }

    @Test
    fun `it should show result field state with figure before the collision`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                .ppp
                ..p.
                ##..
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)

        val expectedResultField = """
            ....
            ..p.
            .ppp
            ##p.
            ....
            ...#
        """.trimIndent()
        assertEquals(expectedResultField, messages[0])
    }

    @Test
    fun `it should show result field state with figure on the bottom if is on the bottom already`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ....
                ....
                ....
                #.p.
                .ppp
                ..p#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)

        val expectedResultField = """
            ....
            ....
            ....
            #.p.
            .ppp
            ..p#
        """.trimIndent()
        assertEquals(expectedResultField, messages[0])
    }

    @Test
    fun `it should show result field state with figure on the collision if is on the collision already`() {
        // Given
        val inputMock = object: MainInput {
            override fun ifFileExist(filePath: String): Boolean = true

            override fun readFileAsString(filePath: String): String = """
                6 4
                ..p.
                .ppp
                ..p.
                #.#.
                ....
                ...#
            """.trimIndent()
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        assertEquals(1, messages.size)

        val expectedResultField = """
            ..p.
            .ppp
            ..p.
            #.#.
            ....
            ...#
        """.trimIndent()
        assertEquals(expectedResultField, messages[0])
    }
}