package edu.wpi.cs3733.c22.teamC.controllers.login;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.EmployeeDaoImpl;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Main;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javax.sound.sampled.*;

public class SignUpController extends AbstractController {
  @FXML private Button goBackButton;
  @FXML private Button createButton;
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private TextField password2;
  @FXML private ImageView imageViewLeft;
  @FXML private ImageView imageViewRight;
  @FXML private AnchorPane anchorPane;
  String user;
  String psswd;
  String psswd2;

  SimpleAudioPlayer player;

  private static class SimpleAudioPlayer {

    // to store current position
    Long curTime;
    Clip drumbs;

    AudioInputStream sound;
    static String filePath = "untitled.wav";

    // constructor to initialize streams and clip
    public SimpleAudioPlayer()
        throws UnsupportedAudioFileException, IOException, LineUnavailableException {
      // create AudioInputStream object
      sound = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

      // create clip reference
      drumbs = AudioSystem.getClip();

      // open audioInputStream to the clip
      drumbs.open(sound);

      drumbs.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() {
      drumbs.start();
    }

    public void restart()
        throws IOException, LineUnavailableException, UnsupportedAudioFileException {
      drumbs.stop();
      drumbs.close();
      reset();
      curTime = 0L;
      drumbs.setMicrosecondPosition(0);
      this.play();
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
      curTime = 0L;
      drumbs.stop();
      drumbs.close();
    }

    public void reset()
        throws UnsupportedAudioFileException, IOException, LineUnavailableException {
      sound = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
      drumbs.open(sound);
      drumbs.loop(Clip.LOOP_CONTINUOUSLY);
    }
  }

  @FXML
  public void initialize() {
    Image gif =
        new Image(
            Main.class.getResource("/edu/wpi/cs3733.c22.teamC/Views/NUGGIES.gif").toExternalForm());
    AnchorPane.setTopAnchor(imageViewLeft, 0.0);
    AnchorPane.setLeftAnchor(imageViewLeft, 0.0);
    AnchorPane.setBottomAnchor(imageViewLeft, 0.0);
    imageViewLeft.setImage(gif);
    AnchorPane.setTopAnchor(imageViewRight, 0.0);
    AnchorPane.setRightAnchor(imageViewRight, 0.0);
    AnchorPane.setBottomAnchor(imageViewRight, 0.0);
    imageViewRight.setImage(gif);

    try {
      player = new SimpleAudioPlayer();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
    player.play();

    username.setOnKeyPressed(
        key -> {
          if (((KeyEvent) key).getCode() == KeyCode.ENTER) {
            createButtonPressed();
          }
        });

    password.setOnKeyPressed(
        key -> {
          if (((KeyEvent) key).getCode() == KeyCode.ENTER) {
            createButtonPressed();
          }
        });

    password2.setOnKeyPressed(
        key -> {
          if (((KeyEvent) key).getCode() == KeyCode.ENTER) {
            createButtonPressed();
          }
        });
  }

  private void createEmployee() {
    user = username.getText();
    psswd = password.getText();
    psswd2 = password2.getText();
    EmployeeDaoImpl e = DaoSingleton.getEmployeeDao();
    Employee employee = new Employee(user, psswd, null, null, null, null, null, null);
    if (psswd.equals(psswd2)) {
      e.addNode(employee);
    } else {
      System.out.println("Not a working account");
    }
    try {
      player.stop();
    } catch (UnsupportedAudioFileException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (LineUnavailableException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void goBackButtonPressed() throws IOException {
    try {
      player.stop();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
    setNewScene("SignInPage.fxml", goBackButton);
  }

  @FXML
  private void createButtonPressed() {
    psswd = password.getText();
    psswd2 = password2.getText();
    //    System.out.println(
    //        "matches: " + psswd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$"));
    //    System.out.println("Equals:" + psswd.equals(psswd2));
    if (psswd.equals(psswd2)
        && psswd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {
      createEmployee();
      try {
        setNewScene("SignInPage.fxml", createButton);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("POPUP NEEDS TO BE CREATED - PASSWORD OR USERNAME NOT CORRECT");
    }
  }
}
