object ConsoleInput {
  def main(args: Array[String]) {
  	println("Please enter a ticker symbol:")
	val symbol = Console.readLine
	println("Ok, got it, you own " + symbol)
  }
}