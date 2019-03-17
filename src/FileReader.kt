import java.io.File

class FileReader {

    fun readFileLineByLine(fileName: String): List<String> {
        val readLines = mutableListOf<String>()

        File(fileName).forEachLine { dataLine ->
            if(dataLine.first().isDigit()) {
                readLines.add(dataLine)
                println(dataLine)
            }
        }

        return readLines
    }
}
