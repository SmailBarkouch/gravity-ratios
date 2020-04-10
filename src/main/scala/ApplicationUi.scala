import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.{BorderPane, StackPane}

class ApplicationUi(borderPane: BorderPane,
                    systemApi: SystemApi,
                    canvas: Canvas) {
  def getScene(): Scene = {
    val stackPane = new StackPane()
    stackPane.setStyle("-fx-background-color: black")

    stackPane.setMinWidth(830)
    borderPane.setLeft(stackPane)
    borderPane.setRight(systemApi.showStats())

    systemApi.constructGraphics(stackPane)
    new Scene(borderPane)

  }

}
