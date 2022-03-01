package edu.wpi.cs3733.c22.teamC.controllers;

import edu.wpi.cs3733.c22.teamC.Main;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Duration;

public abstract class AbstractController {
  protected ControllerMediator controllerMediator = ControllerMediator.getInstance();

  @FXML
  protected Parent loadFxml(String fxmlFileName) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    System.out.println(fxmlFileName);

    Parent root =
        loader.load(Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/" + fxmlFileName));
    return root;
  }

  @FXML
  protected FXMLLoader getLoader(String fxmlFileName) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/" + fxmlFileName));
    return loader;
  }
  /**
   * Switches the root of the current scene to the fxml file given, and uses a button within the
   * scene to setRoot
   *
   * @param fxmlFileName path of the fxml file to be loaded
   * @param button the button that will be used to switch scenes
   * @throws IOException to handle failed IO operations
   */
  @FXML
  protected void setNewScene(String fxmlFileName, Button button) throws IOException {
    Parent root = loadFxml(fxmlFileName);
    button.getScene().setRoot(root);
  }

  @FXML
  protected void setNewScene(Parent root, Button button) throws IOException {
    button.getScene().setRoot(root);
  }

  protected void buttonSwitch(String fxmlFileName, Button button) throws IOException {
    setNewScene(fxmlFileName, button); // for legacy code
  }

  @FXML
  protected void backButtonPressed() throws IOException {
    controllerMediator.backButtonPressed();
  }

  public void angryWiggle(ArrayList<Node> nodes) {
    for (Node n : nodes) {
      ScaleTransition scaleTransition = new ScaleTransition();
      scaleTransition.setNode(n);

      scaleTransition.setByY(.15);
      scaleTransition.setByX(.15);
      scaleTransition.setDuration(Duration.millis(100));
      scaleTransition.setCycleCount(2);
      scaleTransition.setAutoReverse(true);

      /*TranslateTransition translateTransition = new TranslateTransition();
      translateTransition.setNode(n);

      translateTransition.setByX(10);
      translateTransition.setDuration(Duration.millis(100));
      translateTransition.setCycleCount(2);
      translateTransition.setAutoReverse(true);

      translateTransition.play();*/
      scaleTransition.play();
    }
  }
}
