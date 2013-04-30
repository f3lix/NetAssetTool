import scala.actors._
import Actor._

object FindTotalWorthConcurrent {
	def getTotalWorth() = {
		val symbolsAndUnits = StockPriceFinder.getTickerAndUnits

		val caller = self

		println("Today is " + new java.util.Date())
		println("Ticker  Units  Closing Price($)  Total Value($)")

		var startTime = System.nanoTime()

		symbolsAndUnits.keys.foreach {
			symbol =>
			actor {
				caller ! (symbol, StockPriceFinder.getLatestClosingPrice(symbol))
			}
		}

		val netWorth = (0.0 /: (1 to symbolsAndUnits.size)) {
			(worth, index) => receive {
				case (symbol: String, latestClosingPrice: Double) =>
				val units = symbolsAndUnits(symbol)
				val value = units * latestClosingPrice
				println("%-7s  %-5d  %-16f  %f".format(symbol, units, latestClosingPrice, value))
				worth + value
			}
		}

		val endTime = System.nanoTime()

		println("The total value of your investments is $" + netWorth)
		println("Took %f seconds".format((endTime - startTime)/1000000000.0))
	}
}