object UseXML {
	def useXML() = {
		val XMLFragment = 
		<symbols>
			<symbol ticker="AAPL"><units>200</units></symbol>
			<symbol ticker="IBM"><units>215</units></symbol>
		</symbols>

		println("Ticker\tUnits")
		XMLFragment match {
			case <symbols>{ symbolNodes @ _* }</symbols> =>
				for(symbolNode @ <symbol>{ _* }</symbol> <- symbolNodes) {
					println("%-7s %s".format(symbolNode \ "@ticker", (symbolNode \ "units").text))
				}
		}
	}
}