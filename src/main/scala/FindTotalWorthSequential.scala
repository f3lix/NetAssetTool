object FindTotalWorthSequential {
	def getTotalWorth() = {
		val symbolsAndUnits = StockPriceFinder.getTickerAndUnits

		println("Today is " + new java.util.Date())
		println("Ticker  Units  Closing Price($)  Total Value($)")

		val startTime = System.nanoTime()

		val netWorth = (0.0 /: symbolsAndUnits) {
			(worth, symbolsAndUnits) =>
			val (symbol, units) = symbolsAndUnits
			val latestClosingPrice = StockPriceFinder.getLatestClosingPrice(symbol)
			val value = units * latestClosingPrice
			println("%-7s  %-5d  %-16f  %f".format(symbol, units, latestClosingPrice, value))
			worth + value
		}

		val endTime = System.nanoTime()

		println("The total value of your investments is $" + netWorth)
		println("Took %f seconds".format((endTime - startTime)/1000000000.0))
	}
}