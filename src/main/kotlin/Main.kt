package tetrisSimple

import tetrisSimple.io.MainInput
import tetrisSimple.io.MainOutput
import tetrisSimple.logic.parseField
import tetrisSimple.logic.playGame
import java.io.File

fun main(args: Array<String>) {
    val output: MainOutput = object : MainOutput {
        override fun printLine(msg: String) {
            println(msg)
        }
    }

    val input: MainInput = object : MainInput {
        override fun ifFileExist(filePath: String): Boolean = File(filePath).exists()
        override fun readFileAsString(filePath: String): String = File(filePath).readText()
    }

    mainHandler(args, output, input)
}

fun mainHandler(args: Array<String>, output: MainOutput, input: MainInput) {
    if (args.isEmpty()) {
        output.printLine(Messages.NO_ARGS)
        return
    }

    val inputFilePath = args[0]
    if (!input.ifFileExist(inputFilePath)) {
        output.printLine(Messages.INPUT_FILE_DOES_NOT_EXIST)
        return
    }

    val printEachStep: Boolean = try {
        if (args[1] == "-printEachStep") {
            true
        } else {
            output.printLine(Messages.WRONG_SECOND_ARG)
            return
        }
    } catch (e: IndexOutOfBoundsException) {
        false
    }

    val field = try {
        parseField(input.readFileAsString(inputFilePath))
    } catch (e: Exception) {
        null
    }

    if (field == null) {
        output.printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    } else {
        val resultField = playGame(field, printEachStep)
        output.printLine(resultField)
    }
}

object Messages {
    val NO_ARGS = """
        Hello! No input file argument was passed. To pass an argument, use this as an example (arguments in [] mean they can be omitted):
        > gradle run --args="input.txt [-printEachStep]"
    """.trimIndent()

    val INPUT_FILE_DOES_NOT_EXIST = """
        Passed input file doesn't exist.
    """.trimIndent()

    val WRONG_SECOND_ARG = """
        The second argument is not '-printEachStep'. You can pass only '-printEachStep' as the second argument.
    """.trimIndent()

    val INPUT_FILE_HAS_WRONG_CONTENT = """
        Passed input file has wrong content.
    """.trimIndent()
}