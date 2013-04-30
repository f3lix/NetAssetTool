import scala.io._
import scala.xml._
import java.net.URL

object StockPriceFinder {
	def getLatestClosingPrice(symbol: String) = {
			val url = "http://ichart.finance.yahoo.com/table.csv?s=" +
				symbol + "&a=00$b=01$c=" + new java.util.Date().getYear

			val data = scala.io.Source.fromURL(url).mkString
			val mostRecentData = data.split("\n")(1)
			val closingPrice = mostRecentData.split(",")(4).toDouble
			closingPrice
		}

	def getTickerAndUnits() = {
		val stockAndUnitsXML = XML.load("stocks.xml")
		
		(Map[String, Int]() /: (stockAndUnitsXML \ "symbol")) {
			(map, symbolNode) =>
			val ticker = (symbolNode \ "@ticker").toString
			val units = (symbolNode \ "units").text.toInt
			map.updated(ticker, units)
		}
	}
}