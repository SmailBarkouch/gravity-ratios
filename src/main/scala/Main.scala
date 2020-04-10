import javafx.application.Application
import javafx.scene.canvas.Canvas
import javafx.scene.layout.BorderPane
import javafx.scene.shape.Sphere
import javafx.stage.Stage

object Main {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {
  override def start(stage: Stage): Unit = {
    stage.setResizable(false)
    stage.setWidth(1030)
    stage.setHeight(600)
    val applicationUi =
      new ApplicationUi(
        new BorderPane(),
        new SystemApi(
          new Astro(new Sphere(40), 100, 20),
          new Astro(new Sphere(10), 100, 10)
        ),
        new Canvas()
      )

    stage.setTitle("Gravity Ratios - Smail Barkouch")

    stage.setScene(applicationUi.getScene())
    stage.show()
  }
}
