package tetrisSimple

import tetrisSimple.io.MainInput
import tetrisSimple.io.MainOutput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*

class MainKtTest {

    // Mock
    private lateinit var outputMock: MainOutput
    private lateinit var inputMock: MainInput

    @BeforeEach
    fun setUp() {
        outputMock = mock(MainOutput::class.java)
        inputMock = mock(MainInput::class.java)
    }

    @Test
    fun `it should show a message with How-to-use instructions if there is no input file arg`() {
        // When
        mainHandler(emptyArray(), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.NO_ARGS)
    }

    @Test
    fun `it should check if file does exist and show a message if not`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(false)

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_DOES_NOT_EXIST)
    }

    @Test
    fun `it should check if second argument is '-printEachStep' and show a message if not`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)

        // When
        mainHandler(arrayOf("input.txt", "not-the-printEachStep-argument"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.WRONG_SECOND_ARG)
    }

    @Test
    fun `it should parse input file and throw an error if it is invalid`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("Wrong input file body")

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input is empty`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn(null)

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains too much expressions in first line`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains only column count`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains only row count`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains no expressions in first line`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""

                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains expressions on second line`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""

                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains field on first line`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                ..p.
                6 4
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains field on third line`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains only field`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains no field`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input is inverted`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
                6 4
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains less rows than expected`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains more rows than expected`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                5 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains less columns than expected`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .pp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains more columns than expected`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 3
                ..p
                .pp
                ..p
                #...
                ...
                ...
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains unexpected symbols for row count`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                a 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains unexpected symbols for column count`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 a
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains unexpected symbols`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .pap
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains no figure`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ....
                ....
                ....
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains two figures`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                ....
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains two same figures`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ....
                .ppp
                ....
                #ppp
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show error message if input contains diagonal p`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ....
                ...p
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        verify(outputMock).printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    }

    @Test
    fun `it should show result field state with figure on the bottom if there are no collision`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            ....
            ....
            ....
            #.p.
            .ppp
            ..p#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should show result field state with figure before the collision`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                ##..
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            ....
            ..p.
            .ppp
            ##p.
            ....
            ...#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should show result field state with figure on the bottom if is on the bottom already`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ....
                ....
                ....
                #.p.
                .ppp
                ..p#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            ....
            ....
            ....
            #.p.
            .ppp
            ..p#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should show result field state with figure on the collision if is on the collision already`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                #.#.
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            ..p.
            .ppp
            ..p.
            #.#.
            ....
            ...#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should print each step with figure on the bottom if there are no collision and '-printEachStep' flag is passed`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                #...
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt", "-printEachStep"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            STEP 0:
            ..p.
            .ppp
            ..p.
            #...
            ....
            ...#

            STEP 1:
            ....
            ..p.
            .ppp
            #.p.
            ....
            ...#

            STEP 2:
            ....
            ....
            ..p.
            #ppp
            ..p.
            ...#

            STEP 3:
            ....
            ....
            ....
            #.p.
            .ppp
            ..p#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should print each step with figure before the collision if '-printEachStep' flag is passed`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                ##..
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt", "-printEachStep"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            STEP 0:
            ..p.
            .ppp
            ..p.
            ##..
            ....
            ...#

            STEP 1:
            ....
            ..p.
            .ppp
            ##p.
            ....
            ...#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should print each step with figure on the bottom if is on the bottom already and '-printEachStep' flag is passed`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ....
                ....
                ....
                #.p.
                .ppp
                ..p.
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt", "-printEachStep"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            STEP 0:
            ....
            ....
            ....
            #.p.
            .ppp
            ..p.
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }

    @Test
    fun `it should print each step with figure on the collision if is on the collision already and '-printEachStep' flag is passed`() {
        // Given
        `when`(inputMock.ifFileExist(anyString())).thenReturn(true)
        `when`(inputMock.readFileAsString(anyString())).thenReturn("""
                6 4
                ..p.
                .ppp
                ..p.
                #.#.
                ....
                ...#
            """.trimIndent())

        // When
        mainHandler(arrayOf("input.txt", "-printEachStep"), outputMock, inputMock)

        // Then
        val expectedResultField = """
            STEP 0:
            ..p.
            .ppp
            ..p.
            #.#.
            ....
            ...#
        """.trimIndent()
        verify(outputMock).printLine(expectedResultField)
    }
}