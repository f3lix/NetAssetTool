import scala.actors._
import Actor._

object NetAssetStockPriceHelper {
	val symbolsAndUnits = StockPriceFinder.getTickerAndUnits

	def getInitialTableValues: Array[Array[Any]] = {
		val empty = new Array[Array[Any]](0, 0)

		(empty /: symbolsAndUnits) { (data, element) =>
			val (symbol, units) = element
			data ++ Array(List(symbol, units, "?", "?").toArray)
		}
	}

	def fetchPrice(updater: Actor) = actor {
		val caller = self
		symbolsAndUnits.keys.foreach { symbol =>
			actor {
				caller ! (symbol, StockPriceFinder.getLatestClosingPrice(symbol))
			}
		}

		val netWorth = (0.0 /: (1 to symbolsAndUnits.size)) {
				(worth, index) => receive {
				case (symbol: String, latestClosingPrice: Double) =>
				val units = symbolsAndUnits(symbol)
				val value = units * latestClosingPrice
				worth + value
			}
    	}
		updater ! netWorth
	}
}