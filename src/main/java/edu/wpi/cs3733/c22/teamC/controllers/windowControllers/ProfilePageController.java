package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerMediator;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ProfilePageController {
  @FXML ImageView imageView;

  @FXML Pane passPane;

  @FXML FlowPane flowPane;

  @FXML Label firstNameLabel;
  @FXML Label lastNameLabel;
  @FXML Label jobTypeLabel;

  @FXML JFXButton changeButton;

  @FXML PasswordField oldPass;
  @FXML PasswordField newPass1;
  @FXML PasswordField newPass2;

  String[] picNames = {
    "Bear", "Bunny", "Cat", "Deer", "Dog", "Duck", "Fox", "Pig", "Sheep", "Skunk"
  };

  @FXML
  void initialize() {
    ControllerMediator controllerMediator = ControllerMediator.getInstance();

    if (LoggedInUser.getCurrentUser().get_username().equals("cpage"))
      for (int i = 0; i < picNames.length; i++) picNames[i] = "Kevin";

    firstNameLabel.setText(LoggedInUser.getCurrentUser().get_firstName());
    lastNameLabel.setText(LoggedInUser.getCurrentUser().get_lastName());
    jobTypeLabel.setText(LoggedInUser.getCurrentUser().get_serviceType());
    imageView.setImage(LoggedInUser.getProfilePic());

    for (String s : picNames) {
      ImageView pic = new ImageView(ImageLoader.loadImage(s));
      pic.setFitHeight(60);
      pic.setFitWidth(60);
      pic.setOnMousePressed(
          evt -> {
            LoggedInUser.setProfilePic(s);
            imageView.setImage(LoggedInUser.getProfilePic());
            changeButton.setVisible(true);
            imageView.setVisible(true);
            flowPane.setVisible(false);
            try {
              controllerMediator.updateProfilePic();
            } catch (IOException e) {
              System.out.println("failed to update profile pic");
            }
          });
      flowPane.getChildren().add(pic);
    }
  }

  @FXML
  void changeButtonPressed() {
    changeButton.setVisible(false);
    imageView.setVisible(false);
    flowPane.setVisible(true);
  }

  @FXML
  void changePassButtonPressed() {
    passPane.setVisible(true);
  }

  @FXML
  void cancelButtonPressed() {
    passPane.setVisible(false);
    oldPass.setText("");
    newPass1.setText("");
    newPass2.setText("");
  }

  @FXML
  void confirmButtonPressed() {
    if (!oldPass.getText().equals(LoggedInUser.getCurrentUser().get_password())) {
      ScaleTransition scaleTransition = new ScaleTransition();

      scaleTransition.setByY(.15);
      scaleTransition.setByX(.15);
      scaleTransition.setDuration(Duration.millis(100));
      scaleTransition.setCycleCount(2);
      scaleTransition.setAutoReverse(true);

      scaleTransition.setNode(oldPass);
      scaleTransition.play();
    } else if (!newPass1.getText().equals(newPass2.getText())
        && !newPass1.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {

      ScaleTransition scaleTransition = new ScaleTransition();

      scaleTransition.setByY(.15);
      scaleTransition.setByX(.15);
      scaleTransition.setDuration(Duration.millis(100));
      scaleTransition.setCycleCount(2);
      scaleTransition.setAutoReverse(true);

      scaleTransition.setNode(newPass1);
      scaleTransition.play();

      scaleTransition = new ScaleTransition();

      scaleTransition.setByY(.15);
      scaleTransition.setByX(.15);
      scaleTransition.setDuration(Duration.millis(100));
      scaleTransition.setCycleCount(2);
      scaleTransition.setAutoReverse(true);

      scaleTransition.setNode(newPass2);
      scaleTransition.play();
    } else {
      LoggedInUser.changePassword(newPass1.getText());
      passPane.setVisible(false);
    }
  }
}
