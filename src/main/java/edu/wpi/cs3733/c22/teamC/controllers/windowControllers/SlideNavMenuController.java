package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import edu.wpi.cs3733.c22.teamC.controllers.ServiceRequestController;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class SlideNavMenuController extends ServiceRequestController {
  @FXML private JFXButton equipDelivButton;
  @FXML private JFXButton saniServButton;
  @FXML private JFXButton launServButton;
  @FXML private JFXButton medDelivButton;
  @FXML private JFXButton langInterpButton;
  @FXML private JFXButton maintReqButton;
  @FXML private JFXButton itButton;
  @FXML private JFXButton transportButton;
  @FXML private JFXButton giftButton;
  @FXML private JFXButton religiousButton;
  @FXML private JFXButton securityButton;
  @FXML private JFXButton APIButton;

  @FXML ImageView laundryIcon;
  @FXML ImageView interpIcon;
  @FXML ImageView itIcon;
  @FXML ImageView sanitationIcon;
  @FXML ImageView equipIcon;
  @FXML ImageView maintenanceIcon;
  @FXML ImageView medsIcon;
  @FXML ImageView transportIcon;
  @FXML ImageView giftIcon;
  @FXML ImageView religiousIcon;
  @FXML ImageView securityIcon;
  @FXML ImageView APIIcon;

  @FXML private BorderPane borderPane;
  @FXML private ScrollPane scrollPane;
  @FXML private FlowPane flowPane;

  @FXML private TextField searchBox;
  private ArrayList<Button> jfxButtonList = new ArrayList<Button>();

  @FXML
  public void initialize() {
    laundryIcon.setImage(ImageLoader.loadImage("Laundry"));
    interpIcon.setImage(ImageLoader.loadImage("Language"));
    itIcon.setImage(ImageLoader.loadImage("IT"));
    sanitationIcon.setImage(ImageLoader.loadImage("Sanitation"));
    equipIcon.setImage(ImageLoader.loadImage("EquipDelivery"));
    maintenanceIcon.setImage(ImageLoader.loadImage("Maintenance"));
    medsIcon.setImage(ImageLoader.loadImage("Meds"));
    transportIcon.setImage(ImageLoader.loadImage("Transport"));
    giftIcon.setImage(ImageLoader.loadImage("Gifts"));
    religiousIcon.setImage(ImageLoader.loadImage("Religion"));
    securityIcon.setImage(ImageLoader.loadImage("Security"));
    APIIcon.setImage(ImageLoader.loadImage("IT"));

    // BE CAREFUL, ADDING BUTTONS THAT DON'T EXIST ON SlideNavMenu.fxml WILL BREAK SEARCH
    jfxButtonList.addAll(
        FXCollections.observableArrayList(
            saniServButton,
            launServButton,
            medDelivButton,
            langInterpButton,
            maintReqButton,
            equipDelivButton,
            itButton,
            transportButton,
            giftButton,
            religiousButton,
            securityButton,
            APIButton));

    scrollPane
        .heightProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              flowPane.setMinHeight((double) newValue);
            });
  }

  public BorderPane getBorderPane() {
    return borderPane;
  }

  @FXML
  private void searchBoxEntered() {
    jfxButtonList.forEach(
        button -> {
          if (!button.getText().toUpperCase().contains(searchBox.getText().toUpperCase())) {
            button.setVisible(false);
            button.setManaged(false);
          } else {
            button.setVisible(true);
            button.setManaged(true);
          }
        });
  }
}
