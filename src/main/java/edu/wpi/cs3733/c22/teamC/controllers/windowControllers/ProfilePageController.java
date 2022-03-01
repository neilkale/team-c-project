package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ProfilePageController {
  @FXML ImageView imageView;

  @FXML FlowPane flowPane;

  @FXML Label firstNameLabel;
  @FXML Label lastNameLabel;
  @FXML Label jobTypeLabel;

  @FXML JFXButton changeButton;

  String[] picNames = {
    "Bear", "Bunny", "Cat", "Deer", "Dog", "Duck", "Fox", "Pig", "Sheep", "Skunk"
  };

  @FXML
  void initialize() {
    if (LoggedInUser.getCurrentUser().get_username().equals("cpage"))
      for (int i = 0; i < picNames.length; i++) picNames[i] = "Kevin";

    firstNameLabel.setText(LoggedInUser.getCurrentUser().get_firstName());
    lastNameLabel.setText(LoggedInUser.getCurrentUser().get_lastName());
    jobTypeLabel.setText(LoggedInUser.getCurrentUser().get_Service_Type());
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
}
