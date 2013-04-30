object ConsoleInput {
  def getInput() = {
  	println("Please enter a ticker symbol:")
	val symbol = Console.readLine
	println("Ok, got it, you own " + symbol)
  }
}