package tetrisSimple

import tetrisSimple.io.MainInput
import tetrisSimple.io.MainOutput
import tetrisSimple.logic.makeFieldToString
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

    val field = try {
        parseField(input.readFileAsString(inputFilePath))
    } catch (e: Exception) {
        null
    }

    if (field == null) {
        output.printLine(Messages.INPUT_FILE_HAS_WRONG_CONTENT)
    } else {
        val resultField = playGame(field)
        output.printLine(makeFieldToString(resultField))
    }
}

object Messages {
    val NO_ARGS = """
        Hello! No input file argument was passed. To pass an argument, use this as an example:
        > gradle run --args="input.txt"
    """.trimIndent()

    val INPUT_FILE_DOES_NOT_EXIST = """
        Passed input file doesn't exist.
    """.trimIndent()

    val INPUT_FILE_HAS_WRONG_CONTENT = """
        Passed input file has wrong content.
    """.trimIndent()
}