package edu.wpi.cs3733.c22.teamC.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CardSwipeController extends AbstractController {
  @FXML private TextField textField;
  @FXML private JFXButton cancelButton;
  @FXML private Label failMessage;
  @FXML private JFXSpinner spinner;

  @FXML
  void initialize() {
    textField.requestFocus();
    failMessage.setVisible(false);
  }

  // Check Credentials Button - sends it back to the employee query
  private boolean checkCredentials(String id) {
    EmployeeQuery employeeQuery = new EmployeeQuery();
    Employee e = employeeQuery.findNodeByID(id);
    LoggedInUser.signInEmployee(e.get_id());
    if (e != null) { // username exists
      if (e.get_id().equals(id)) { // password is correct
        //        if (e.get_access().equals("admin")) {
        //          DatabaseConnection connection = new DatabaseConnection();
        //          connection.startServerClientDbConnection();
        //          System.out.println(connection);
        //        }
        return true;
        // TODO: implement different levels of access
      }
    }
    return false;
  }

  @FXML
  // restarts the textfield
  void screenClicked() {
    textField.requestFocus();
  }

  // automatically done bruh
  void submitButtonPressed() {
    if (checkCredentials(textField.getText().substring(1, 10))) {
      FXMLLoader loader = null;
      try {
        loader = getLoader("TabPane.fxml");
      } catch (IOException e) {
        e.printStackTrace();
      }
      Parent root = null;
      try {
        root = loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
      controllerMediator.setTabPaneController(loader.getController());
      try {
        setNewScene(root, cancelButton);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("FAIL");
      failMessage.setVisible(true);
      textField.setText("");
      textField.requestFocus();
      spinner.setVisible(false);
      // ControllerUtil.popUpMessage(
      //    "Incorrect User/Password", "There was an error with your Card Read");
    }
  }

  // This is the cancel button and takes you back to the home page
  @FXML
  void cancelButtonPressed() throws IOException {
    setNewScene("SignInPage.fxml", cancelButton);
  }

  @FXML
  void keyPressed() {
    if (textField.getText().contains("||0000")) {
      submitButtonPressed();
    } else if (!textField.getText().equals("")) {
      spinner.setVisible(true);
    }
  }
}
