import scala.io.Source

object ReadingFile {
	def readingFile() = {
		println("The content of the file you read is:")
		Source.fromFile("symbols.txt").foreach { print }
		println
	}
}