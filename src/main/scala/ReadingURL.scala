import scala.io.Source
import java.net.URL

object ReadingURL {
	def readingURL() = {
		val source = Source.fromURL(new URL("http://www.scala-lang.org/docu/files/api/index.html"))
		println(source.getLine(5)) // index start from 1
	}
}