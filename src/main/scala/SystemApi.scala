import javafx.animation.{Interpolator, PathTransition}
import javafx.geometry.Insets
import javafx.scene.control.{Label, TextField}
import javafx.scene.layout.{StackPane, VBox}
import javafx.scene.shape.{Ellipse, Sphere}
import javafx.util.Duration

import scala.util.Try

case class Astro(sphere: Sphere = new Sphere(100),
                 var distance: Float = 100,
                 var mass: Float = 100);

class SystemApi(primeAstro: Astro = new Astro(),
                secondAstro: Astro = new Astro(),
                var transition: PathTransition = new PathTransition()) {

  def getPrimeAstro: Astro = primeAstro
  def getSecondAstro: Astro = secondAstro

  def constructGraphics(stackPane: StackPane) = {
    showPrimeAstro(stackPane)
    showSecondaryAstro(stackPane)
    moveAnimation(stackPane.getWidth, stackPane.getHeight)
  }

  private def showPrimeAstro(stackPane: StackPane) =
    stackPane.getChildren.addAll(primeAstro.sphere)

  private def showSecondaryAstro(stackPane: StackPane) = {
    stackPane.getChildren.addAll(secondAstro.sphere)
  }

  def moveAnimation(width: Double, height: Double) = {
    val path = new Ellipse()
    path.setCenterX(10)
    path.setCenterY(0)
    path.setRadiusX(secondAstro.distance)
    path.setRadiusY(secondAstro.distance)

    Try {
      transition.stop()
    }
    transition = new PathTransition()
    transition.setPath(path)
    transition.setNode(secondAstro.sphere)
    transition.setInterpolator(Interpolator.LINEAR)
    transition.setDuration(Duration.seconds(.009 * secondAstro.distance))
    transition.setOrientation(PathTransition.OrientationType.NONE)
    transition.setCycleCount(1000)
    transition.play()
  }

  private def displayVectors(foGDisplay: TextField,
                             astroGravityDisplay: TextField): Unit = {

    foGDisplay.setText(
      "FoG: " + findFoG(
        primeAstro.mass,
        secondAstro.mass,
        (secondAstro.sphere.getRadius + primeAstro.sphere.getRadius + secondAstro.distance).toFloat
      )
    )

    astroGravityDisplay.setText(
      "Gravity (MP): " + findAstroGravity(
        primeAstro.mass,
        (secondAstro.sphere.getRadius + primeAstro.sphere.getRadius + secondAstro.distance).toFloat
      )
    )

  }

  private def findAstroGravity(mass: Float, radius: Float): Float =
    ((6.6726E-11 * mass) / math.pow(radius, 2)).toFloat

  private def findFoG(mass1: Float, mass2: Float, radius: Float): Float =
    (findAstroGravity(mass1, radius) * mass2)

  def showStats(): VBox = {
    val vBox = new VBox()
    vBox.setPadding(new Insets(10, 10, 15, 10))
    constructStates(vBox)

    vBox.setStyle(
      "-fx-border-color: blue;\n"
        + "-fx-border-insets: 5;\n"
        + "-fx-border-width: 3;\n"
        + "-fx-border-style: dashed;\n"
    )

    vBox
  }

  private def constructStates(vBox: VBox) = {
    val radiusLabel1 = new Label("Astro 1 Radius")
    val radiusLabel2 = new Label("Astro 2 Radius")

    val radiusField1 = new TextField()
    radiusField1.setText(primeAstro.sphere.getRadius.toString)
    val radiusField2 = new TextField()
    radiusField2.setText(secondAstro.sphere.getRadius.toString)

    val massLabel1 = new Label("Astro 1 Mass")
    val massLabel2 = new Label("Astro 2 Mass")

    val massField1 = new TextField()
    massField1.setText(primeAstro.mass.toString)
    val massField2 = new TextField()
    massField2.setText(secondAstro.mass.toString)

    val distanceLabel = new Label("Distance (NOT RADIAL)")

    val distanceField = new TextField()
    distanceField.setText(secondAstro.distance.toString)

    val foGDisplay = new TextField()
    foGDisplay.setDisable(true)
    val astroGravityDisplay = new TextField()
    astroGravityDisplay.setDisable(true)

    displayVectors(foGDisplay, astroGravityDisplay)

    bindStates(
      radiusField1,
      radiusField2,
      massField1,
      massField2,
      distanceField,
      foGDisplay,
      astroGravityDisplay
    )

    vBox.getChildren.addAll(
      radiusLabel1,
      radiusField1,
      radiusLabel2,
      radiusField2,
      massLabel1,
      massField1,
      massLabel2,
      massField2,
      distanceLabel,
      distanceField,
      foGDisplay,
      astroGravityDisplay
    )
  }

  private def bindStates(radiusField1: TextField,
                         radiusField2: TextField,
                         massField1: TextField,
                         massField2: TextField,
                         distanceField: TextField,
                         foGDisplay: TextField,
                         astroGravityDisplay: TextField) = {
    radiusField1.setOnAction(_ => {
      primeAstro.sphere.setRadius(radiusField1.getText.toFloat)
      displayVectors(foGDisplay, astroGravityDisplay)
      moveAnimation(513, 300)
    })

    radiusField2.setOnAction(_ => {
      secondAstro.sphere.setRadius(radiusField2.getText.toFloat)
      displayVectors(foGDisplay, astroGravityDisplay)
      moveAnimation(513, 300)
    })

    massField1.setOnAction(_ => {
      primeAstro.mass = massField1.getText.toFloat
      displayVectors(foGDisplay, astroGravityDisplay)
    })

    massField2.setOnAction(_ => {
      secondAstro.mass = massField2.getText().toFloat
      displayVectors(foGDisplay, astroGravityDisplay)
    })

    distanceField.setOnAction(_ => {
      secondAstro.distance = distanceField.getText().toFloat
      displayVectors(foGDisplay, astroGravityDisplay)
      moveAnimation(397, 232)
    })
  }
}
