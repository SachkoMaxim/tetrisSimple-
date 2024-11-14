package tetrisSimple.io

interface MainInput {
    fun ifFileExist(filePath: String): Boolean
    fun readFileAsString(filePath: String): String
}