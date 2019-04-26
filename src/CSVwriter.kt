import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter

class CSVwriter @Throws(FileNotFoundException::class)
constructor(fileName: String) {

    private val sb: StringBuilder = StringBuilder()
    private val pw: PrintWriter = PrintWriter(File("$fileName.csv"))

    fun appendToFile(string: String) {
        sb.append(string)
    }

    fun nextColumn() {
        sb.append(';')
    }

    fun nextLine() {
        sb.append('\n')
    }

    fun saveFile() {
        pw.write(sb.toString())
        pw.close()
    }
}
