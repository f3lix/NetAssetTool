import scala.xml._
import java.io._

object ReadWriteXML {
	def main(args: Array[String]) {
		val stocksAndUnits = XML.load("stocks.xml")
		//println(stocksAndUnits.getClass())
		//println("Loaded file has " + (stocksAndUnits \\ "symbol").size + " symbol elements")

		println("Ticker\tUnits")
		stocksAndUnits match {
			case <symbols>{ symbolNodes @ _* }</symbols> =>
				for(symbolNode @ <symbol>{ _* }</symbol> <- symbolNodes) {
					println("%-7s %s".format(symbolNode \ "@ticker", (symbolNode \ "units").text))
				}
		}

		val stocksAndUnitsMap = (Map[String, Int]() /: (stocksAndUnits \ "symbol")) {
			(map, symbolNode) => 
			val ticker = (symbolNode \ "@ticker").toString
			val units = (symbolNode \ "units").text.toInt
			//map(ticker) = units
			map.updated(ticker, units)
		}

		def updateUnitsAndCreateXML(element: (String, Int)) = {
			val (ticker, units) = element
			<symbol ticker={ ticker }>
				<units>{ units + 1 }</units>
			</symbol>
		}

		val updatedStocksAndUnitsXML =
		<symbols>
			{ stocksAndUnitsMap.map { updateUnitsAndCreateXML } }
		</symbols>

		//println(updatedStocksAndUnitsXML)

		XML.save ("stocks2.xml", updatedStocksAndUnitsXML)
		println("The saved file containts " + (XML.load("stocks2.xml") \\ "symbol").size + " symbol elements")
	}
}