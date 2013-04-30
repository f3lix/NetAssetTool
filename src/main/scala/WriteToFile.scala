import java.io._

object WriteToFile {
	def writingFile() = {
		val writer = new PrintWriter(new File("symbols.txt"))
		writer write "AAPL"
		writer.close()
		println("Wrote to file.")
	}
}