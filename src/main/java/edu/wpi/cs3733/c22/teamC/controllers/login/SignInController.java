package edu.wpi.cs3733.c22.teamC.controllers.login;

import com.jfoenix.controls.JFXPasswordField;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerMediator;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SignInController extends AbstractController {
  @FXML private Button loginButton;
  @FXML private Button createButton;
  @FXML private JFXPasswordField psswd;
  @FXML private TextField username;

  String passwordTest;

  private ControllerMediator controllerMediator = ControllerMediator.getInstance();

  @FXML
  public void initialize() {
    psswd.setOnKeyPressed(
        key -> {
          if (((KeyEvent) key).getCode() == KeyCode.ENTER) {
            loginButtonPressed();
          }
        });

    username.setOnKeyPressed(
        key -> {
          if (((KeyEvent) key).getCode() == KeyCode.ENTER) {
            loginButtonPressed();
          }
        });
  }

  @FXML
  private void loginButtonPressed() {
    // TODO: keep this button unclickable if username/psswd text fields are empty
    if (checkCredentials(username.getText(), psswd.getText())) {
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
        setNewScene(root, loginButton);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("WRONG USERNAME/PASSWORD. TRY AGAIN.");
      ControllerUtil.popUpMessage(
          "Incorrect User/Password", "There was an error with your Username/Password");
    }
  }

  private boolean checkCredentials(String user, String pass) {
    EmployeeQuery employeeQuery = new EmployeeQuery();
    Employee e = employeeQuery.findNodeByUsername(user);
    if (e != null) { // username exists
      if (e.get_password().equals(pass)) { // password is correct
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
  private void passwordEntry() throws IOException {
    // TODO: account verification and other navigation
    passwordTest = psswd.getText();
    System.out.println(passwordTest);
  }

  @FXML
  private void createButtonPressed() throws IOException {
    // TODO: Implement account creation
    setNewScene("SignUpPage.fxml", createButton);
  }

  @FXML
  private void cardButtonPressed() throws IOException {
    // TODO: Implement account creation
    setNewScene("CardSwipe.fxml", createButton);
  }
}
