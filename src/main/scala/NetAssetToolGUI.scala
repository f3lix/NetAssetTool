import scala.swing._
import event._
import scala.actors._
import Actor._
import java.awt.Color

object NetAssetToolGUI extends SimpleGUIApplication {

	def top = mainFrame

	val mainFrame = new MainFrame {
		title = "NetAssetTool"
		val dateLabel = new Label { text = "Last updated: -----" }

		val valuesTable = new Table(
			NetAssetStockPriceHelper.getInitialTableValues,
			Array("Ticker", "Units", "Price", "Value")) {
			showGrid = true
			gridColor = Color.BLACK
		}

		val updateButton = new Button { text = "Update" }
		val netAssetLabel = new Label { text = "Net Asset : 0.0" }

		contents = new BoxPanel(Orientation.Vertical) {
			contents += dateLabel
			contents += valuesTable
			contents += new ScrollPane(valuesTable)

			contents += new FlowPanel {
				contents += updateButton
				contents += netAssetLabel
			}
		}

		listenTo(updateButton)

		reactions += {
			case ButtonClicked(button) =>
			button.enabled = false
			NetAssetStockPriceHelper.fetchPrice(uiUpdater)
    	}

		val uiUpdater = new Actor {
			def act = {
				loop {
					react {
						case (symbol: String, units: Int, price: Double, value: Double)
						=> updateTable(symbol, units, price, value)
						
						case netAsset =>
						netAssetLabel.text = "Net Asset:" + netAsset
						dateLabel.text = "Last updated : " + new java.util.Date()
						updateButton.enabled = true
					}
				}
			}
		}

		uiUpdater.start()

		def updateTable(symbol: String, units: Int, price: Double, value: Double) {
			for (i <- 0 until valuesTable.rowCount) {
				if (valuesTable(i, 0) == symbol) {
					valuesTable(i, 2) = price
					valuesTable(i, 3) = value
				}
			}
		}
	}
}