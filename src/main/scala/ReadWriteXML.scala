import scala.xml._

object ReadWriteXML {
	def main(args: Array[String]) {
		val stocksAndUnits = XML.load("stocks.xml")
		println(stocksAndUnits.getClass())
		println("Loaded file has " + (stocksAndUnits \\ "symbol").size + " symbol elements")
	}
}