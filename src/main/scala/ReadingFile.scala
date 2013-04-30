import scala.io.Source

object ReadingFile {
	def main(args: Array[String]) {
		println("The content of the file you read is:")
		Source.fromFile("symbols.txt").foreach { print }
		println
	}
}